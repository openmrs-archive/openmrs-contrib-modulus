databaseChangeLog = {

	changeSet(author: "herooftime (generated)", id: "changelog") {
		// TODO add changes and preconditions here
	}

	include file: 'initial.groovy'

	include file: 'MOD-42-add-owner-and-maintainers.groovy'

	include file: 'MOD-42-owner-to-string.groovy'

	include file: 'MOD-56-create-auth-tables.groovy'
}
