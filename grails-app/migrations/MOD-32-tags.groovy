databaseChangeLog = {

	changeSet(author: "action (generated)", id: "1407186857999-1") {
		createTable(tableName: "module_tag") {
			column(name: "module_tags_id", type: "bigint")

			column(name: "tag_id", type: "bigint")
		}
	}

	changeSet(author: "action (generated)", id: "1407186857999-5") {
		dropIndex(indexName: "FK1BF9AE4131745", tableName: "tag")
	}

	changeSet(author: "action (generated)", id: "1407186857999-6") {
		createIndex(indexName: "FKCC277F47A3042FA5", tableName: "module_tag") {
			column(name: "module_tags_id")
		}
	}

	changeSet(author: "action (generated)", id: "1407186857999-7") {
		createIndex(indexName: "FKCC277F47AE93DA4F", tableName: "module_tag") {
			column(name: "tag_id")
		}
	}

	changeSet(author: "action (generated)", id: "1407186857999-8") {
		dropColumn(columnName: "module_id", tableName: "tag")
	}

	changeSet(author: "action (generated)", id: "1407186857999-3") {
		addForeignKeyConstraint(baseColumnNames: "module_tags_id", baseTableName: "module_tag", constraintName: "FKCC277F47A3042FA5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "module", referencesUniqueColumn: "false")
	}

	changeSet(author: "action (generated)", id: "1407186857999-4") {
		addForeignKeyConstraint(baseColumnNames: "tag_id", baseTableName: "module_tag", constraintName: "FKCC277F47AE93DA4F", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "tag", referencesUniqueColumn: "false")
	}
}
