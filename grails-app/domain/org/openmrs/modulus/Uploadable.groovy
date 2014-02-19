package org.openmrs.modulus

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Provides domain fields for handling uploaded files. Works in tandem with <code>RestfulUploadController</code>
 * @see org.openmrs.modulus.RestfulUploadController
 */
class Uploadable {

    String filename
    String contentType
    byte[] rawFile
    String downloadURL

    String path


    static constraints = {
        rawFile maxSize: 10 * 1024 * 1024, nullable: true
        path nullable: true
        downloadURL maxLength: 255, nullable: true
        filename maxLength: 255, nullable: true
        contentType nullable: true
    }

    def beforeUpdate() {
        if (isDirty('path')) {
            // delete the old file
            Path oldPath = Paths.get(this.getPersistentValue('path'))
            Files.deleteIfExists(oldPath)
        }
    }
}
