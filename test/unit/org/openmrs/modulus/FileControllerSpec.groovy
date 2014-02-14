package org.openmrs.modulus

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(FileController)
@Mock([File])
class FileControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
    }

    void "Uploading a new file creates a valid File instance"() {
        setup:
            def numberOfFiles = File.count()

        when: "A file is POSTed without an id"
            // "Hello, world." in utf-8 hex
            request.content = [0x48, 0x65, 0x6c, 0x6c, 0x6f, 0x2c, 0x20, 0x77, 0x6f, 0x72, 0x6c, 0x64, 0x2e, 0x0a]
            params.name = "hello.txt"
            request.method = "POST"
            controller.uploadNewFile()

        then: "A new file is created"
            File.count() > numberOfFiles

            def id = response.json.id
            def instance = File.get(id)

            "hello.txt" == response.json.filename == instance.filename
            "Hello, world." == new String(instance.rawFile)
    }
}
