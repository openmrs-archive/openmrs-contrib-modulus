databaseChangeLog = {

	changeSet(author: "herooftime (generated)", id: "changelog") {
		// TODO add changes and preconditions here
	}

	include file: 'initial.groovy'

	include file: 'MOD-42-add-owner-and-maintainers.groovy'
}
