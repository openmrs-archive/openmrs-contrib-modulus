package org.openmrs.modulus

import grails.transaction.Transactional
import org.apache.commons.io.IOUtils

import javax.activation.MimetypesFileTypeMap
import java.nio.file.Paths

@Transactional
class UploadableService {

    static mime = new MimetypesFileTypeMap()

    def grailsApplication

    /**
     * Upload a file (such as a release of a module) to the repository, and store it in a release object.
     * @param instance Uploadable domain object to upload to
     * @param bytes Binary contents of the file
     * @param name Filename to save as
     * @return the domain object uploaded to
     */
    def uploadFile(Uploadable instance, byte[] bytes, String name) {
        def uploadDestination = grailsApplication.config.modulus.uploadDestination

        def destDir = Paths.get("$uploadDestination/${instance.class.name}/${instance.id}").toFile()
        destDir.mkdirs()

        def dest = new File(destDir.getAbsolutePath() + '/' + name)
        dest.createNewFile()

        // do the transfer
        def output = dest.newOutputStream()
        IOUtils.write(bytes, output)
        output.close()

        instance.path = dest.getAbsolutePath()
        instance.contentType = mime.getContentType(name)
        instance.filename = name

        instance
    }

    /**
     * Upload a file from a Java File object
     * @param instance Uploadable domain object to upload to
     * @param file File representing the file being uploaded
     * @return the domain object uploaded to
     */
    def uploadFile(Uploadable instance, File file) {
        uploadFile(instance, file.bytes, file.name)
    }
}
