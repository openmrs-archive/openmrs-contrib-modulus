package org.openmrs.modulus

import java.util.logging.Logger

/**
 * Created by Elliott Williams on 6/10/14.
 */
class TestUtils {

    static Logger log = Logger.getLogger(TestUtils.class.getName());

    static download(String address) {
        log.info("Downloading ${address}")
        File.createTempFile('test-module', '.omod').with {
            deleteOnExit()

            def stream = newOutputStream()
            stream << new URL(address).openStream()
            stream.close()

            return it
        }
    }
}