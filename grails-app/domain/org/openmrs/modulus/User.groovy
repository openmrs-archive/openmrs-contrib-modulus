package org.openmrs.modulus

class User {

	transient springSecurityService

	String username
    String fullname

	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

    static hasMany = [oAuthIDs: OAuthID]

	static transients = ['springSecurityService']

	static constraints = {
        username nullable: false, maxSize: 100
        fullname maxSize: 100, nullable: true
		password blank: false
	}

	static mapping = {
        table '`user`' // User is a MySQL reserved word; backticks force Hibernate to escape it
		password column: '`password`'
	}

    static searchable = {
        username spellCheck: "include"
    }

    static marshalling = {
        ignore 'password', 'enabled', 'accountExpired', 'accountLocked', 'passwordExpired', 'oAuthIDs'
    }

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
