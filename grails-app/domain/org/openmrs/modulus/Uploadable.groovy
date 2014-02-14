package org.openmrs.modulus

import grails.validation.Validateable

import javax.activation.MimetypesFileTypeMap

/**
 * Provides domain fields for handling uploaded files. Works in tandem with <code>RestfulUploadController</code>
 * @see RestfulUploadController
 */
class Uploadable {

    String filename
    String contentType
    byte[] rawFile

    static mimeMap = new MimetypesFileTypeMap()

    def beforeValidate() {
        if (this.filename) {
            this.contentType = mimeMap.getContentType(this.filename)
        }
    }

    def beforeUpdate() {
        if (isDirty('filename') && this.filename) {
            this.contentType = mimeMap.getContentType(this.filename)
        }
    }

    // TODO ensure rawFile and filename validation is working correctly

    static constraints = {
        rawFile maxSize: 10 * 1024 * 1024, nullable: true
        filename maxSize: 255, nullable: true, validator: { val, obj ->
            log.debug("filename=${obj.filename}")
            if (val && !obj.filename) "MustBeSubmittedTogetherError"
        }
        contentType nullable: true
    }
}
