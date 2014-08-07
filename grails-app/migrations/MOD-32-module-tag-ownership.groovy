databaseChangeLog = {

	changeSet(author: "action (generated)", id: "1407438704822-1") {
		createTable(tableName: "module_tags") {
			column(name: "tag_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "module_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "action (generated)", id: "1407438704822-3") {
		addPrimaryKey(columnNames: "module_id, tag_id", tableName: "module_tags")
	}

	changeSet(author: "action (generated)", id: "1407438704822-4") {
		dropForeignKeyConstraint(baseTableName: "tag_modules", baseTableSchemaName: "modulus", constraintName: "FK84B653E2E4131745")
	}

	changeSet(author: "action (generated)", id: "1407438704822-5") {
		dropForeignKeyConstraint(baseTableName: "tag_modules", baseTableSchemaName: "modulus", constraintName: "FK84B653E2AE93DA4F")
	}

	changeSet(author: "action (generated)", id: "1407438704822-8") {
		createIndex(indexName: "FKB8C86A0CAE93DA4F", tableName: "module_tags") {
			column(name: "tag_id")
		}
	}

	changeSet(author: "action (generated)", id: "1407438704822-9") {
		createIndex(indexName: "FKB8C86A0CE4131745", tableName: "module_tags") {
			column(name: "module_id")
		}
	}

	changeSet(author: "action (generated)", id: "1407438704822-11") {
		dropTable(tableName: "tag_modules")
	}

	changeSet(author: "action (generated)", id: "1407438704822-6") {
		addForeignKeyConstraint(baseColumnNames: "module_id", baseTableName: "module_tags", constraintName: "FKB8C86A0CE4131745", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "module", referencesUniqueColumn: "false")
	}

	changeSet(author: "action (generated)", id: "1407438704822-7") {
		addForeignKeyConstraint(baseColumnNames: "tag_id", baseTableName: "module_tags", constraintName: "FKB8C86A0CAE93DA4F", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "tag", referencesUniqueColumn: "false")
	}
}
