databaseChangeLog = {

	changeSet(author: "action (generated)", id: "1421124171597-1") {
		createTable(tableName: "module_required_modules") {
			column(name: "module_id", type: "bigint")

			column(name: "required_modules_string", type: "varchar(255)")
		}
	}

	changeSet(author: "action (generated)", id: "1421124171597-6") {
		createIndex(indexName: "FK26B9CC5AE4131745", tableName: "module_required_modules") {
			column(name: "module_id")
		}
	}

	changeSet(author: "action (generated)", id: "1421124171597-4") {
		addForeignKeyConstraint(baseColumnNames: "module_id", baseTableName: "module_required_modules", constraintName: "FK26B9CC5AE4131745", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "module", referencesUniqueColumn: "false")
	}
}
