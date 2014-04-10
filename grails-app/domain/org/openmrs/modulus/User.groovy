package org.openmrs.modulus

class User {

    String fullname
    String username

    def getWikiProfileURL() {
        "https://wiki.openmrs.org/display/~$username"
    }

    static mapping = {
        table '`user`' // User is a MySQL reserved word; backticks force Hibernate to escape it
    }

    static transients = ['wikiProfileURL']

    static constraints = {
        username nullable: false, maxSize: 100
        fullname maxSize: 100, nullable: true
    }

    @Override
    String toString() {
        "${super.toString()} (${this.username})"
    }

    static searchable = {
        username spellCheck: "include"
    }

    static marshalling = {
        attribute 'username'
    }
}
