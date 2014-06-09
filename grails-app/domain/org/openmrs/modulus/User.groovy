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

        showRoles {
            virtual {
                roles { user, json->
                    json.value(user.marshallAuthorities())
                }
            }
        }
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

    Set<String> marshallAuthorities() {
        getAuthorities().collect { it.authority }
    }

    boolean hasRole(Role r) {
        def result = UserRole.findWhere(user: this, role: r)
        if (result) {
            true
        } else {
            false
        }
    }

    boolean hasRole(String r) {
        def role = Role.findWhere(authority: r)
        return hasRole(role)
    }

    boolean isCurrentUser() {
        springSecurityService.getCurrentUser() == this
    }
}
