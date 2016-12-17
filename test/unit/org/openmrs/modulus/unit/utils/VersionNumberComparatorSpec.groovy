package org.openmrs.modulus.unit.utils

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.openmrs.modulus.utils.VersionNumberComparator
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class VersionNumberComparatorSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "should correctly compare two version numbers"() {
        given:
        String olderVersion = "2.1.9"
        String newerVersion = "2.1.10"

        when:
        def result = new VersionNumberComparator().compare(olderVersion, newerVersion)

        then:
        result < 0
    }

    void "should equate commit and non-commit version numbers"() {
        given:
        String a = "1.9.7.60bd9b"
        String b = "1.9.7"

        when:
        def result = new VersionNumberComparator().compare(a, b)

        then:
        result == 0
    }

    void "should compare version strings of different lengths"() {
        given:
        String a = "1.9.7"
        String b = "1.9"

        when:
        def result = new VersionNumberComparator().compare(a, b)

        then:
        result == 1
    }
}
