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
    String downloadURL

    String path


    static constraints = {
        path nullable: true
        downloadURL maxSize: 255, nullable: true
        filename maxSize: 255, nullable: true
        contentType nullable: true
    }

    def beforeUpdate() {
        if (isDirty('path') && this.getPersistentValue('path')) {
            // delete the old file
            Path oldPath = Paths.get(this.getPersistentValue('path'))
            Files.deleteIfExists(oldPath)
        }
    }
}
