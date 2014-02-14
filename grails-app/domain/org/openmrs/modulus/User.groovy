package org.openmrs.modulus

class User {

    String fullname
    String username

    static constraints = {
        username blank: false, maxSize: 100
        fullname maxSize: 100
    }

    @Override
    String toString() {
        "${this.fullname} (${this.username})"
    }
}
