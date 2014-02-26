package org.openmrs.modulus

class User {

    String fullname
    String username

    static mapping = {
        table '`user`' // Release is a Postgres reserved word; backticks force Hibernate to escape it
    }

    static constraints = {
        username blank: false, maxSize: 100
        fullname maxSize: 100
    }

    @Override
    String toString() {
        "${this.fullname} (${this.username})"
    }
}
