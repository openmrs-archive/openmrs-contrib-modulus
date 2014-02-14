package org.openmrs.modulus

import grails.transaction.Transactional

@Transactional
class UserService {

    User addUser(String username, String full) {
        def user = new User(username: username, fullname: full)
        user.save()
        user
    }
}
