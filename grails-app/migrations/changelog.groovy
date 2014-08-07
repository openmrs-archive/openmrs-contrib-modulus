databaseChangeLog = {

	changeSet(author: "herooftime (generated)", id: "changelog") {
		// TODO add changes and preconditions here
	}

	include file: 'initial.groovy'

	include file: 'MOD-42-add-owner-and-maintainers.groovy'

	include file: 'MOD-42-owner-to-string.groovy'

	include file: 'MOD-56-create-auth-tables.groovy'

  include file: 'MOD-56-combine-security-tables.groovy'

	include file: 'MOD-56-no-acl.groovy'

	include file: 'MOD-56-oauth-token.groovy'

	include file: 'MOD-32-tags.groovy'

	include file: 'MOD-32-many-to-many.groovy'

	include file: 'MOD-32-module-tag-ownership.groovy'
}
