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

# Create transfer table based on module schema
-- DROP TABLE IF EXISTS transfer_module;

-- CREATE TABLE `transfer_module` (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `version` bigint(20) NOT NULL,
--   `date_created` datetime NOT NULL,
--   `description` longtext DEFAULT NULL,
--   `documentationurl` varchar(255) DEFAULT NULL,
--   `last_updated` datetime NOT NULL,
--   `legacy_id` varchar(255) DEFAULT NULL,
--   `name` varchar(255) DEFAULT NULL,
--   `slug` varchar(255) DEFAULT NULL,
--   PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=module_next_id() DEFAULT CHARSET=utf8;


INSERT INTO
	module (
		version,
		date_created,
		documentationurl,
		last_updated,
		legacy_id,
		name,
		slug,
    description
	)
SELECT
	1,
	date_created,
	homepage,
	NOW(),
	id,
	name,
	slugify(id),
  module_description(id)
FROM
	modulerepository.module;SELECT '  Migrating release table...' AS ' ';

-- DROP TABLE IF EXISTS transfer_uploadable;
-- CREATE TABLE `transfer_uploadable` (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `version` bigint(20) NOT NULL,
--   `content_type` varchar(255) DEFAULT NULL,
--   `filename` varchar(255) DEFAULT NULL,
--   `path` varchar(255) DEFAULT NULL,
--   `class` varchar(255) NOT NULL,
--   `date_created` datetime NOT NULL,
--   `download_count` int(11) NOT NULL,
--   `last_updated` datetime NOT NULL,
--   `module_id` bigint(20) NOT NULL,
--   `module_version` varchar(255) DEFAULT NULL,
-- --   `released_by_id` bigint(20) DEFAULT NULL,
--   `requiredomrsversion` varchar(255) DEFAULT NULL,
--   `releases_idx` int(11) DEFAULT NULL,
--   PRIMARY KEY (`id`),
--   KEY `FKF188BADBE4131745` (`module_id`)
-- --   KEY `FKF188BADBA3EA0C17` (`released_by_id`),
-- --   CONSTRAINT `FKF188BADBA3EA0C17` FOREIGN KEY (`released_by_id`) REFERENCES `transfer_user` (`id`),
--   -- CONSTRAINT `FKF188BADBE4131745` FOREIGN KEY (`module_id`) REFERENCES `transfer_module` (`id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=release_next_id() DEFAULT CHARSET=utf8;

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
  
-- SELECT '  Merging transformed data to modulus tables...' AS ' ';

-- INSERT INTO module (id, version, date_created, description, documentationurl,
--   last_updated, legacy_id, `name`, slug)
--   SELECT * FROM transfer_module;

-- INSERT INTO uploadable (id, version, content_type, filename,
--   `path`, class, date_created, download_count, last_updated,
--   module_id, module_version, requiredomrsversion, releases_idx)
--   SELECT * FROM transfer_uploadable;

SELECT '  Migration finished.' AS ' ';