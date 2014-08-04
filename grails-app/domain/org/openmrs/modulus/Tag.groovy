package org.openmrs.modulus

class Tag {

    String name
    String description

    String color = "grey"

    static constraints = {
        name unique: true
        description maxSize: 10000, nullable: true
    }
}
