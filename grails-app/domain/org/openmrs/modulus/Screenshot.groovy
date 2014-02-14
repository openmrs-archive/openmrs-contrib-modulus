package org.openmrs.modulus

class Screenshot {

    byte[] rawFile

    String description
    User uploadedBy



//    static belongsTo = [module: Module]

    static constraints = {
        rawFile maxSize: 1024 * 1024 * 2 // 2MB
        description maxSize: 140
    }
}
