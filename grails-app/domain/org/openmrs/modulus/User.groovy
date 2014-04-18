package org.openmrs.modulus

class User {

    String fullname
    String username

    static mapping = {
        table '`user`' // User is a MySQL reserved word; backticks force Hibernate to escape it
    }

    static constraints = {
        username nullable: false, maxSize: 100
        fullname maxSize: 100, nullable: true
    }

    static searchable = {
        username spellCheck: "include"
    }

    static marshalling = {
        attribute 'username'
    }
}
