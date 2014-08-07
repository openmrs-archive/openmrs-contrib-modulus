package org.openmrs.modulus

class Tag {

    String name
    String description

    String color = "grey"

    static hasMany = [modules:Module]

    static constraints = {
        name unique: true
        description maxSize: 10000, nullable: true
    }

    static marshalling = {
        ignore 'class', 'modules'

        tagShow {
            ignore 'class'
            deep 'modules'
        }
    }
}
