databaseChangeLog = {

	changeSet(author: "herooftime (generated)", id: "1402604546053-1") {
		addNotNullConstraint(columnDataType: "varchar(255)", columnName: "password", tableName: "user")
	}

	changeSet(author: "herooftime (generated)", id: "1402604546053-2") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "oauthid", constraintName: "FK9C00D1F27771EDA5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}
}
