databaseChangeLog = {

	changeSet(author: "herooftime (generated)", id: "1398091714584-1") {
		addColumn(tableName: "module") {
			column(name: "owner", type: "varchar(255)")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1398091714584-2") {
		dropForeignKeyConstraint(baseTableName: "module", baseTableSchemaName: "modulus", constraintName: "FKC04BA66CE3589DBD")
	}

	changeSet(author: "herooftime (generated)", id: "1398091714584-3") {
		dropColumn(columnName: "owner_id", tableName: "module")
	}
}
