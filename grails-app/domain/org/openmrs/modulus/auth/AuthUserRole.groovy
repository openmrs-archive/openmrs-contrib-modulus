package org.openmrs.modulus.auth

import org.apache.commons.lang.builder.HashCodeBuilder

class AuthUserRole implements Serializable {

	private static final long serialVersionUID = 1

	AuthUser authUser
	Role role

	boolean equals(other) {
		if (!(other instanceof AuthUserRole)) {
			return false
		}

		other.authUser?.id == authUser?.id &&
			other.role?.id == role?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (authUser) builder.append(authUser.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static AuthUserRole get(long authUserId, long roleId) {
		AuthUserRole.where {
			authUser == AuthUser.load(authUserId) &&
			role == Role.load(roleId)
		}.get()
	}

	static AuthUserRole create(AuthUser authUser, Role role, boolean flush = false) {
		new AuthUserRole(authUser: authUser, role: role).save(flush: flush, insert: true)
	}

	static boolean remove(AuthUser u, Role r, boolean flush = false) {

		int rowCount = AuthUserRole.where {
			authUser == AuthUser.load(u.id) &&
			role == Role.load(r.id)
		}.deleteAll()

		rowCount > 0
	}

	static void removeAll(AuthUser u) {
		AuthUserRole.where {
			authUser == AuthUser.load(u.id)
		}.deleteAll()
	}

	static void removeAll(Role r) {
		AuthUserRole.where {
			role == Role.load(r.id)
		}.deleteAll()
	}

	static mapping = {
		id composite: ['role', 'authUser']
		version false
	}
}
