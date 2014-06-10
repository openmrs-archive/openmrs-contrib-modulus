package org.openmrs.modulus

import grails.transaction.Transactional
import org.codehaus.groovy.grails.exceptions.GrailsException

import java.util.jar.JarFile
import java.util.zip.ZipError
import java.util.zip.ZipException
import java.util.zip.ZipInputStream

@Transactional
class OmodParserService {

    /**
     * Parse metadata within a module file
     * @param omodFile Reference to an OMOD file
     * @return map of module metadata relevant to the Module Repository data model. can be
     */
    def getMetadata(File omodFile) {

        def config = getConfigXml(omodFile)

        return [
                id: config.id.text(),
                name: config.name.text(),
                version: config.version.text(),
                require_version: config.require_version.text(),

                // Remove excess whitespace from the description
                description: config.description.text().replaceAll("\\s\\s+", "")
        ]
    }

    /**
     * Convenience method if only the path to a file is available
     * @param omodFilePath path to OMOD
     */
    def getMetadata(String omodFilePath) {
        getMetadata(new File(omodFilePath))
    }

    /**
     * Parse an omod file, and return its config.xml available for XML slurping
     * @param omod File reference to .omod
     * @return XmlSlurper of the omod's config.xml
     */
    protected getConfigXml(File omodFile) {
        def omod = new JarFile(omodFile)
        def text
        omod.entries().each { entry ->
            if (entry.name == "config.xml") {
                text =  omod.getInputStream(entry).text
            }

        }

        if (!text) return null

        return new XmlParser(false, false, true).parseText(text)
    }

    protected ensureOmodFile(File omodFile) throws NonOmodException {
        def error = {
            throw new NonOmodException("File does not appear to be an omod")
        }

        if (!omodFile.name.endsWith(".omod")) { return error() }

        try {
            new JarFile(omodFile)
        } catch (ZipException ze) {
            return error()
        }


    }
}

class NonOmodException extends GrailsException {
    NonOmodException() {}

    NonOmodException(String message) {
        super(message)
    }

    NonOmodException(Throwable cause) {
        super(cause)
    }

    NonOmodException(String message, Throwable cause) {
        super(message, cause)
    }
}
