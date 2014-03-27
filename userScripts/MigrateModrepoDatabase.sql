DELIMITER //

DROP FUNCTION IF EXISTS slugify//

CREATE FUNCTION slugify(p_name varchar(255))
  RETURNS varchar(255)
  BEGIN
    DECLARE name varchar(255);
    SET name = LOWER(p_name);
    SET name = REPLACE(name, '.', '-');
    RETURN name;
  END//


DROP FUNCTION IF EXISTS module_description//

CREATE FUNCTION module_description(module_id varchar(255))
  RETURNS varchar(1024)
  BEGIN
    DECLARE descrip varchar(1024);

    SELECT description
    INTO descrip
    FROM modulerepository.module_version
    WHERE (id=module_id)
    ORDER BY version DESC
    LIMIT 1;
      
    RETURN descrip;
  END//

DROP FUNCTION IF EXISTS get_release_idx//

CREATE FUNCTION get_release_idx(module_id VARCHAR(255), release_version VARCHAR(255))
  RETURNS INT
  BEGIN
    DECLARE count INT;
    SET @i = -1;

    SELECT POSITION
    INTO count
    FROM (
      SELECT id, version, @i:=@i+1 as POSITION
        FROM modulerepository.module_version
        WHERE id=module_id
        ORDER BY version
      ) idx_table
    WHERE (version=release_version);

    RETURN count;
  END//

DROP FUNCTION IF EXISTS module_next_id//

CREATE FUNCTION module_next_id()
  RETURNS BIGINT(21)
  BEGIN
    DECLARE count BIGINT(21);

    SELECT AUTO_INCREMENT
    INTO count
    FROM information_schema.tables
    WHERE table_name = 'module'
    AND table_schema = DATABASE( );

    RETURN count;
  END//

DROP FUNCTION IF EXISTS release_next_id//

CREATE FUNCTION release_next_id()
  RETURNS BIGINT(21)
  BEGIN
    DECLARE count BIGINT(21);

    SELECT AUTO_INCREMENT
    INTO count
    FROM information_schema.tables
    WHERE table_name = 'release'
    AND table_schema = DATABASE( );

    RETURN count;
  END//


DELIMITER ;
SELECT '  Migrating module table...' AS ' ';


INSERT INTO
	module (
		version,
		date_created,
		documentationurl,
		last_updated,
		legacy_id,
		name,
		slug,
    description,
    download_count
	)
SELECT
	1,
	date_created,
	homepage,
	NOW(),
	id,
	name,
	slugify(id),
  module_description(id),
  0
FROM
	modulerepository.module;SELECT '  Migrating release table...' AS ' ';


INSERT INTO
  uploadable(
    version,
    class,
    date_created,
    download_count,
    last_updated,
    module_id,
    module_version,
    requiredomrsversion,
    releases_idx
  )

  SELECT
    1,
    'org.openmrs.modulus.Release',
    oldrel.date_created,
    (SELECT COUNT(*)
      FROM modulerepository.module_log_download
      WHERE (id=oldrel.id) AND (version=oldrel.version)),
    oldrel.date_changed,
    newmod.id,
    oldrel.version,
    oldrel.require_openmrs_version,
    get_release_idx(oldrel.id, oldrel.version)

      
    
    FROM modulerepository.module_version as oldrel
    LEFT JOIN module as newmod
    ON (oldrel.id = newmod.legacy_id);


SELECT '  Migration finished.' AS ' ';