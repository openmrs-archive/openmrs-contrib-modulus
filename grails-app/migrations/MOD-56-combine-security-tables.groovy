databaseChangeLog = {

	changeSet(author: "herooftime (generated)", id: "1399318433095-1") {
		createTable(tableName: "acl_class") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "acl_classPK")
			}

			column(name: "class", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-2") {
		createTable(tableName: "acl_entry") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "acl_entryPK")
			}

			column(name: "ace_order", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "acl_object_identity", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "audit_failure", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "audit_success", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "granting", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "mask", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "sid", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-3") {
		createTable(tableName: "acl_object_identity") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "acl_object_idPK")
			}

			column(name: "object_id_class", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "entries_inheriting", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "object_id_identity", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "owner_sid", type: "bigint")

			column(name: "parent_object", type: "bigint")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-4") {
		createTable(tableName: "acl_sid") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "acl_sidPK")
			}

			column(name: "principal", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "sid", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-5") {
		createTable(tableName: "user_role") {
			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-6") {
		addColumn(tableName: "user") {
			column(name: "account_expired", type: "bit") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-7") {
		addColumn(tableName: "user") {
			column(name: "account_locked", type: "bit") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-8") {
		addColumn(tableName: "user") {
			column(name: "enabled", type: "bit") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-9") {
		addColumn(tableName: "user") {
			column(name: "password", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-10") {
		addColumn(tableName: "user") {
			column(name: "password_expired", type: "bit") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-11") {
		addPrimaryKey(columnNames: "role_id, user_id", constraintName: "user_rolePK", tableName: "user_role")
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-12") {
		dropForeignKeyConstraint(baseTableName: "auth_user_role", baseTableSchemaName: "modulus", constraintName: "FK4BCFDA9378899A66")
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-13") {
		dropForeignKeyConstraint(baseTableName: "auth_user_role", baseTableSchemaName: "modulus", constraintName: "FK4BCFDA93756CA1D5")
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-21") {
		createIndex(indexName: "class_uniq_1399318433007", tableName: "acl_class", unique: "true") {
			column(name: "class")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-22") {
		createIndex(indexName: "FK5302D47D5B1B0850", tableName: "acl_entry") {
			column(name: "sid")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-23") {
		createIndex(indexName: "FK5302D47D8B9F71F2", tableName: "acl_entry") {
			column(name: "acl_object_identity")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-24") {
		createIndex(indexName: "unique_ace_order", tableName: "acl_entry", unique: "true") {
			column(name: "acl_object_identity")

			column(name: "ace_order")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-25") {
		createIndex(indexName: "FK2A2BB0095C2B98C4", tableName: "acl_object_identity") {
			column(name: "owner_sid")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-26") {
		createIndex(indexName: "FK2A2BB009699FB980", tableName: "acl_object_identity") {
			column(name: "object_id_class")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-27") {
		createIndex(indexName: "FK2A2BB0097FC8265D", tableName: "acl_object_identity") {
			column(name: "parent_object")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-28") {
		createIndex(indexName: "unique_object_id_identity", tableName: "acl_object_identity", unique: "true") {
			column(name: "object_id_class")

			column(name: "object_id_identity")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-29") {
		createIndex(indexName: "unique_principal", tableName: "acl_sid", unique: "true") {
			column(name: "sid")

			column(name: "principal")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-30") {
		createIndex(indexName: "FK143BF46A7771EDA5", tableName: "user_role") {
			column(name: "user_id")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-31") {
		createIndex(indexName: "FK143BF46AD24729C5", tableName: "user_role") {
			column(name: "role_id")
		}
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-32") {
		dropTable(tableName: "auth_user")
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-33") {
		dropTable(tableName: "auth_user_role")
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-14") {
		addForeignKeyConstraint(baseColumnNames: "acl_object_identity", baseTableName: "acl_entry", constraintName: "FK5302D47D8B9F71F2", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "acl_object_identity", referencesUniqueColumn: "false")
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-15") {
		addForeignKeyConstraint(baseColumnNames: "sid", baseTableName: "acl_entry", constraintName: "FK5302D47D5B1B0850", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "acl_sid", referencesUniqueColumn: "false")
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-16") {
		addForeignKeyConstraint(baseColumnNames: "object_id_class", baseTableName: "acl_object_identity", constraintName: "FK2A2BB009699FB980", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "acl_class", referencesUniqueColumn: "false")
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-17") {
		addForeignKeyConstraint(baseColumnNames: "owner_sid", baseTableName: "acl_object_identity", constraintName: "FK2A2BB0095C2B98C4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "acl_sid", referencesUniqueColumn: "false")
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-18") {
		addForeignKeyConstraint(baseColumnNames: "parent_object", baseTableName: "acl_object_identity", constraintName: "FK2A2BB0097FC8265D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "acl_object_identity", referencesUniqueColumn: "false")
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-19") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FK143BF46AD24729C5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "herooftime (generated)", id: "1399318433095-20") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", constraintName: "FK143BF46A7771EDA5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}
}
