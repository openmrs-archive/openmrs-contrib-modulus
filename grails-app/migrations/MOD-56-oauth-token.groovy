databaseChangeLog = {

	changeSet(author: "herooftime (generated)", id: "1399406323169-1") {
		createTable(tableName: "oauthid") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "oauthidPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "access_token", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "provider", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399406323169-3") {
		createIndex(indexName: "FK9C00D1F27771EDA5", tableName: "oauthid") {
			column(name: "user_id")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399406323169-4") {
		createIndex(indexName: "access_token_uniq_1399406322912", tableName: "oauthid", unique: "true") {
			column(name: "access_token")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399406323169-5") {
		createIndex(indexName: "identity_idx", tableName: "oauthid") {
			column(name: "access_token")

			column(name: "provider")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399406323169-2") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "oauthid", constraintName: "FK9C00D1F27771EDA5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}
}
