databaseChangeLog = {

	changeSet(author: "action (generated)", id: "1407180289532-1") {
		createTable(tableName: "module_tag") {
			column(name: "module_tags_id", type: "bigint")

			column(name: "tag_id", type: "bigint")
		}
	}

	changeSet(author: "action (generated)", id: "1407180289532-2") {
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

			column(name: "description", type: "longtext") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "action (generated)", id: "1407180289532-3") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "password", tableName: "user")
	}

	changeSet(author: "action (generated)", id: "1407180289532-6") {
		createIndex(indexName: "FKCC277F47A3042FA5", tableName: "module_tag") {
			column(name: "module_tags_id")
		}
	}

	changeSet(author: "action (generated)", id: "1407180289532-7") {
		createIndex(indexName: "FKCC277F47AE93DA4F", tableName: "module_tag") {
			column(name: "tag_id")
		}
	}

	changeSet(author: "action (generated)", id: "1407180289532-8") {
		createIndex(indexName: "name_uniq_1407180288786", tableName: "tag", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "action (generated)", id: "1407180289532-9") {
		dropColumn(columnName: "releases_idx", tableName: "uploadable")
	}

	changeSet(author: "action (generated)", id: "1407180289532-4") {
		addForeignKeyConstraint(baseColumnNames: "module_tags_id", baseTableName: "module_tag", constraintName: "FKCC277F47A3042FA5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "module", referencesUniqueColumn: "false")
	}

	changeSet(author: "action (generated)", id: "1407180289532-5") {
		addForeignKeyConstraint(baseColumnNames: "tag_id", baseTableName: "module_tag", constraintName: "FKCC277F47AE93DA4F", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "tag", referencesUniqueColumn: "false")
	}
}
