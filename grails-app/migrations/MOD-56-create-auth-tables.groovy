databaseChangeLog = {

	changeSet(author: "herooftime (generated)", id: "1398181963708-1") {
		createTable(tableName: "auth_user") {
			column(name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "auth_userPK")
			}

			column(name: "account_expired", type: "bit")

			column(name: "account_locked", type: "bit")

			column(name: "enabled", type: "bit")

			column(name: "password", type: "varchar(255)")

			column(name: "password_expired", type: "bit")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1398181963708-2") {
		createTable(tableName: "auth_user_role") {
			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "auth_user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "herooftime (generated)", id: "1398181963708-3") {
		createTable(tableName: "role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "herooftime (generated)", id: "1398181963708-4") {
		addPrimaryKey(columnNames: "role_id, auth_user_id", constraintName: "auth_user_rolPK", tableName: "auth_user_role")
	}

	changeSet(author: "herooftime (generated)", id: "1398181963708-7") {
		createIndex(indexName: "FK4BCFDA93756CA1D5", tableName: "auth_user_role") {
			column(name: "role_id")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1398181963708-8") {
		createIndex(indexName: "FK4BCFDA9378899A66", tableName: "auth_user_role") {
			column(name: "auth_user_id")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1398181963708-9") {
		createIndex(indexName: "authority_uniq_1398181963531", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1398181963708-5") {
		addForeignKeyConstraint(baseColumnNames: "auth_user_id", baseTableName: "auth_user_role", constraintName: "FK4BCFDA9378899A66", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "auth_user", referencesUniqueColumn: "false")
	}

	changeSet(author: "herooftime (generated)", id: "1398181963708-6") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "auth_user_role", constraintName: "FK4BCFDA93756CA1D5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}
}
