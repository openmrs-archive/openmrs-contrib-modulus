databaseChangeLog = {

	changeSet(author: "herooftime (generated)", id: "1396642554443-1") {
		createTable(tableName: "module_user") {
			column(name: "module_maintainers_id", type: "bigint")

			column(name: "user_id", type: "bigint")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1396642554443-2") {
		addColumn(tableName: "module") {
			column(name: "owner_id", type: "bigint")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1396642554443-3") {
		dropNotNullConstraint(columnDataType: "varchar(100)", columnName: "fullname", tableName: "user")
	}

	changeSet(author: "herooftime (generated)", id: "1396642554443-7") {
		createIndex(indexName: "FKC04BA66CE3589DBD", tableName: "module") {
			column(name: "owner_id")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1396642554443-8") {
		createIndex(indexName: "FKB8C921BE2E495D89", tableName: "module_user") {
			column(name: "module_maintainers_id")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1396642554443-9") {
		createIndex(indexName: "FKB8C921BE7771EDA5", tableName: "module_user") {
			column(name: "user_id")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1396642554443-4") {
		addForeignKeyConstraint(baseColumnNames: "owner_id", baseTableName: "module", constraintName: "FKC04BA66CE3589DBD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "herooftime (generated)", id: "1396642554443-5") {
		addForeignKeyConstraint(baseColumnNames: "module_maintainers_id", baseTableName: "module_user", constraintName: "FKB8C921BE2E495D89", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "module", referencesUniqueColumn: "false")
	}

	changeSet(author: "herooftime (generated)", id: "1396642554443-6") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "module_user", constraintName: "FKB8C921BE7771EDA5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}
}
