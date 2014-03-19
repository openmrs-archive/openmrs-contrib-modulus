databaseChangeLog = {

	changeSet(author: "herooftime (generated)", id: "1395172600540-1") {
		createTable(tableName: "module") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "LONGTEXT")

			column(name: "documentationurl", type: "VARCHAR(255)")

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "legacy_id", type: "VARCHAR(255)")

			column(name: "name", type: "VARCHAR(100)")

			column(name: "slug", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1395172600540-2") {
		createTable(tableName: "screenshot") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "VARCHAR(140)") {
				constraints(nullable: "false")
			}

			column(name: "module_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "raw_file", type: "MEDIUMBLOB") {
				constraints(nullable: "false")
			}

			column(name: "uploaded_by_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "screenshots_idx", type: "INT")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1395172600540-3") {
		createTable(tableName: "transfer_module") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "LONGTEXT")

			column(name: "documentationurl", type: "VARCHAR(255)")

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "legacy_id", type: "VARCHAR(255)")

			column(name: "name", type: "VARCHAR(255)")

			column(name: "slug", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1395172600540-4") {
		createTable(tableName: "transfer_uploadable") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "content_type", type: "VARCHAR(255)")

			column(name: "downloadurl", type: "VARCHAR(255)")

			column(name: "filename", type: "VARCHAR(255)")

			column(name: "path", type: "VARCHAR(255)")

			column(name: "raw_file", type: "MEDIUMBLOB")

			column(name: "class", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "download_count", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "module_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "module_version", type: "VARCHAR(255)")

			column(name: "requiredomrsversion", type: "VARCHAR(255)")

			column(name: "releases_idx", type: "INT")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1395172600540-5") {
		createTable(tableName: "uploadable") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "content_type", type: "VARCHAR(255)")

			column(name: "downloadurl", type: "VARCHAR(255)")

			column(name: "filename", type: "VARCHAR(255)")

			column(name: "path", type: "VARCHAR(255)")

			column(name: "raw_file", type: "MEDIUMBLOB")

			column(name: "class", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "download_count", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "DATETIME") {
				constraints(nullable: "false")
			}

			column(name: "module_id", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "module_version", type: "VARCHAR(255)")

			column(name: "released_by_id", type: "BIGINT")

			column(name: "requiredomrsversion", type: "VARCHAR(255)")

			column(name: "releases_idx", type: "INT")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1395172600540-6") {
		createTable(tableName: "user") {
			column(autoIncrement: "true", name: "id", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true")
			}

			column(name: "version", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "fullname", type: "VARCHAR(100)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "VARCHAR(100)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "herooftime (generated)", id: "1395172600540-11") {
		createIndex(indexName: "FKF188BADBE4131745", tableName: "transfer_uploadable", unique: "false") {
			column(name: "module_id")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1395172600540-7") {
		addForeignKeyConstraint(baseColumnNames: "module_id", baseTableName: "screenshot", baseTableSchemaName: "modulus", constraintName: "FKE72D8566E4131745", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "module", referencedTableSchemaName: "modulus", referencesUniqueColumn: "false")
	}

	changeSet(author: "herooftime (generated)", id: "1395172600540-8") {
		addForeignKeyConstraint(baseColumnNames: "uploaded_by_id", baseTableName: "screenshot", baseTableSchemaName: "modulus", constraintName: "FKE72D8566AA112AFA", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "modulus", referencesUniqueColumn: "false")
	}

	changeSet(author: "herooftime (generated)", id: "1395172600540-9") {
		addForeignKeyConstraint(baseColumnNames: "module_id", baseTableName: "uploadable", baseTableSchemaName: "modulus", constraintName: "FKF188BADBE4131745", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "module", referencedTableSchemaName: "modulus", referencesUniqueColumn: "false")
	}

	changeSet(author: "herooftime (generated)", id: "1395172600540-10") {
		addForeignKeyConstraint(baseColumnNames: "released_by_id", baseTableName: "uploadable", baseTableSchemaName: "modulus", constraintName: "FKF188BADBA3EA0C17", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user", referencedTableSchemaName: "modulus", referencesUniqueColumn: "false")
	}
}
