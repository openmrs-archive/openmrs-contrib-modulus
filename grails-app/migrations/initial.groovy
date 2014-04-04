databaseChangeLog = {

	changeSet(author: "herooftime (generated)", id: "1396637760301-1") {
		createTable(tableName: "module") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "modulePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "longtext")

			column(name: "documentationurl", type: "varchar(255)")

			column(name: "download_count", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "legacy_id", type: "varchar(255)")

			column(name: "name", type: "varchar(100)")

			column(name: "slug", type: "varchar(255)")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1396637760301-2") {
		createTable(tableName: "screenshot") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "screenshotPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(140)") {
				constraints(nullable: "false")
			}

			column(name: "module_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "raw_file", type: "mediumblob") {
				constraints(nullable: "false")
			}

			column(name: "uploaded_by_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "screenshots_idx", type: "integer")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1396637760301-3") {
		createTable(tableName: "uploadable") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "uploadablePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "content_type", type: "varchar(255)")

			column(name: "downloadurl", type: "varchar(255)")

			column(name: "filename", type: "varchar(255)")

			column(name: "path", type: "varchar(255)")

			column(name: "class", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "download_count", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "module_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "module_version", type: "varchar(255)")

			column(name: "released_by_id", type: "bigint")

			column(name: "requiredomrsversion", type: "varchar(255)")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1396637760301-4") {
		createTable(tableName: "user") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "fullname", type: "varchar(100)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(100)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "herooftime (generated)", id: "1396637760301-9") {
		createIndex(indexName: "FKE72D8566AA112AFA", tableName: "screenshot") {
			column(name: "uploaded_by_id")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1396637760301-10") {
		createIndex(indexName: "FKE72D8566E4131745", tableName: "screenshot") {
			column(name: "module_id")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1396637760301-11") {
		createIndex(indexName: "FKF188BADBA3EA0C17", tableName: "uploadable") {
			column(name: "released_by_id")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1396637760301-12") {
		createIndex(indexName: "FKF188BADBE4131745", tableName: "uploadable") {
			column(name: "module_id")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1396637760301-5") {
		addForeignKeyConstraint(baseColumnNames: "module_id", baseTableName: "screenshot", constraintName: "FKE72D8566E4131745", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "module", referencesUniqueColumn: "false")
	}

	changeSet(author: "herooftime (generated)", id: "1396637760301-6") {
		addForeignKeyConstraint(baseColumnNames: "uploaded_by_id", baseTableName: "screenshot", constraintName: "FKE72D8566AA112AFA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "herooftime (generated)", id: "1396637760301-7") {
		addForeignKeyConstraint(baseColumnNames: "module_id", baseTableName: "uploadable", constraintName: "FKF188BADBE4131745", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "module", referencesUniqueColumn: "false")
	}

	changeSet(author: "herooftime (generated)", id: "1396637760301-8") {
		addForeignKeyConstraint(baseColumnNames: "released_by_id", baseTableName: "uploadable", constraintName: "FKF188BADBA3EA0C17", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}
}
