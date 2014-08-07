databaseChangeLog = {

	changeSet(author: "action (generated)", id: "1407332753257-1") {
		createTable(tableName: "tag_modules") {
			column(name: "module_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "tag_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "action (generated)", id: "1407332753257-3") {
		addPrimaryKey(columnNames: "tag_id, module_id", tableName: "tag_modules")
	}

	changeSet(author: "action (generated)", id: "1407332753257-4") {
		dropForeignKeyConstraint(baseTableName: "module_tag", baseTableSchemaName: "modulus", constraintName: "FKCC277F47A3042FA5")
	}

	changeSet(author: "action (generated)", id: "1407332753257-5") {
		dropForeignKeyConstraint(baseTableName: "module_tag", baseTableSchemaName: "modulus", constraintName: "FKCC277F47AE93DA4F")
	}

	changeSet(author: "action (generated)", id: "1407332753257-8") {
		createIndex(indexName: "FK84B653E2AE93DA4F", tableName: "tag_modules") {
			column(name: "tag_id")
		}
	}

	changeSet(author: "action (generated)", id: "1407332753257-9") {
		createIndex(indexName: "FK84B653E2E4131745", tableName: "tag_modules") {
			column(name: "module_id")
		}
	}

	changeSet(author: "action (generated)", id: "1407332753257-11") {
		dropTable(tableName: "module_tag")
	}

	changeSet(author: "action (generated)", id: "1407332753257-6") {
		addForeignKeyConstraint(baseColumnNames: "module_id", baseTableName: "tag_modules", constraintName: "FK84B653E2E4131745", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "module", referencesUniqueColumn: "false")
	}

	changeSet(author: "action (generated)", id: "1407332753257-7") {
		addForeignKeyConstraint(baseColumnNames: "tag_id", baseTableName: "tag_modules", constraintName: "FK84B653E2AE93DA4F", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "tag", referencesUniqueColumn: "false")
	}
}
