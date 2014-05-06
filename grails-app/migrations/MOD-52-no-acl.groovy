databaseChangeLog = {

	changeSet(author: "herooftime (generated)", id: "1399318960912-1") {
		dropForeignKeyConstraint(baseTableName: "acl_entry", baseTableSchemaName: "modulus", constraintName: "FK5302D47D8B9F71F2")
	}

	changeSet(author: "herooftime (generated)", id: "1399318960912-2") {
		dropForeignKeyConstraint(baseTableName: "acl_entry", baseTableSchemaName: "modulus", constraintName: "FK5302D47D5B1B0850")
	}

	changeSet(author: "herooftime (generated)", id: "1399318960912-3") {
		dropForeignKeyConstraint(baseTableName: "acl_object_identity", baseTableSchemaName: "modulus", constraintName: "FK2A2BB009699FB980")
	}

	changeSet(author: "herooftime (generated)", id: "1399318960912-4") {
		dropForeignKeyConstraint(baseTableName: "acl_object_identity", baseTableSchemaName: "modulus", constraintName: "FK2A2BB0095C2B98C4")
	}

	changeSet(author: "herooftime (generated)", id: "1399318960912-5") {
		dropForeignKeyConstraint(baseTableName: "acl_object_identity", baseTableSchemaName: "modulus", constraintName: "FK2A2BB0097FC8265D")
	}

	changeSet(author: "herooftime (generated)", id: "1399318960912-6") {
		dropIndex(indexName: "class_uniq_1399318433007", tableName: "acl_class")
	}

	changeSet(author: "herooftime (generated)", id: "1399318960912-7") {
		dropIndex(indexName: "unique_ace_order", tableName: "acl_entry")
	}

	changeSet(author: "herooftime (generated)", id: "1399318960912-8") {
		dropIndex(indexName: "unique_object_id_identity", tableName: "acl_object_identity")
	}

	changeSet(author: "herooftime (generated)", id: "1399318960912-9") {
		dropIndex(indexName: "unique_principal", tableName: "acl_sid")
	}

	changeSet(author: "herooftime (generated)", id: "1399318960912-10") {
		dropTable(tableName: "acl_class")
	}

	changeSet(author: "herooftime (generated)", id: "1399318960912-11") {
		dropTable(tableName: "acl_entry")
	}

	changeSet(author: "herooftime (generated)", id: "1399318960912-12") {
		dropTable(tableName: "acl_object_identity")
	}

	changeSet(author: "herooftime (generated)", id: "1399318960912-13") {
		dropTable(tableName: "acl_sid")
	}
}
