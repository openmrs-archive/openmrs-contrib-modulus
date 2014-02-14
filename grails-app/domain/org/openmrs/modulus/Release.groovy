package org.openmrs.modulus

class Release extends Uploadable {

    String moduleVersion
    String requiredOMRSVersion

    Date dateCreated
    Date lastUpdated

    User releasedBy

//    static belongsTo = [module: Module]


    static mapping = {
        table '`release`' // Release is a MySQL reserved word; backticks force Hibernate to escape it
        autoTimestamp true
    }

    static constraints = {
        releasedBy nullable: true
        version unique: true, validator: { val ->
            // TODO implement version validator and comparator
            true
        }
    }
}
