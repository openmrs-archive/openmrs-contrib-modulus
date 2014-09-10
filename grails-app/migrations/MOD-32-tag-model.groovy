databaseChangeLog = {

	changeSet(author: "action (generated)", id: "1409261671185-1") {
		createTable(tableName: "module_tags") {
			column(name: "module_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "tag_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "action (generated)", id: "1409261671185-2") {
		createTable(tableName: "tag") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tagPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "color", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "longtext")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "action (generated)", id: "1409261671185-3") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "password", tableName: "user")
	}

	changeSet(author: "action (generated)", id: "1409261671185-4") {
		addPrimaryKey(columnNames: "module_id, tag_id", tableName: "module_tags")
	}

	changeSet(author: "action (generated)", id: "1409261671185-7") {
		createIndex(indexName: "FKB8C86A0CAE93DA4F", tableName: "module_tags") {
			column(name: "tag_id")
		}
	}

	changeSet(author: "action (generated)", id: "1409261671185-8") {
		createIndex(indexName: "FKB8C86A0CE4131745", tableName: "module_tags") {
			column(name: "module_id")
		}
	}

	changeSet(author: "action (generated)", id: "1409261671185-9") {
		createIndex(indexName: "name_uniq_1409261670830", tableName: "tag", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "action (generated)", id: "1409261671185-10") {
		dropColumn(columnName: "releases_idx", tableName: "uploadable")
	}

	changeSet(author: "action (generated)", id: "1409261671185-11") {
		dropTable(tableName: "module_tag")
	}

	changeSet(author: "action (generated)", id: "1409261671185-5") {
		addForeignKeyConstraint(baseColumnNames: "module_id", baseTableName: "module_tags", constraintName: "FKB8C86A0CE4131745", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "module", referencesUniqueColumn: "false")
	}

	changeSet(author: "action (generated)", id: "1409261671185-6") {
		addForeignKeyConstraint(baseColumnNames: "tag_id", baseTableName: "module_tags", constraintName: "FKB8C86A0CAE93DA4F", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "tag", referencesUniqueColumn: "false")
	}
}
