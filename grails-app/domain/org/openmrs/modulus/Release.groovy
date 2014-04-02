package org.openmrs.modulus

import org.openmrs.modulus.utils.VersionNumberComparator

class Release extends Uploadable implements Comparable<Release> {

    String moduleVersion
    String requiredOMRSVersion

    User releasedBy

    Integer downloadCount = 0

    // Auto-generated:
    Date dateCreated
    Date lastUpdated

    def incrementDownloadCount() {
        this.downloadCount++
        this.module.incrementDownloadCount()
        return this.downloadCount
    }

    static belongsTo = [module: Module]

    static mapping = {
        table '`release`' // Release is a MySQL reserved word; backticks force Hibernate to escape it
        autoTimestamp true
        tablePerHierarchy false
    }


    static constraints = {
        releasedBy nullable: true
        moduleVersion maxSize: 255, nullable: true
        requiredOMRSVersion maxSize: 255, nullable: true
    }


    static def downloadLinkGeneratorService

    def generateDownloadURL() {
        this.downloadURL = downloadLinkGeneratorService.URL("release", this.id, this.filename)
    }

    def afterInsert() {
        if (this.filename) {
            generateDownloadURL()
            this.save()
        }
    }


    def beforeUpdate() {
        if (isDirty('filename') && this.filename) {
            generateDownloadURL()
        }
    }

    /**
     * Compare two releases for sorting by version. Used to produce a SortedSet of releases for each module.
     * The set will be sorted by descending version number (with highest version number in the lowest index)
     * @param other Release to compare to
     * @return negative integer = less than, positive integer = greater than, zero = equal
     */
    public int compareTo(Release other) {
        new VersionNumberComparator().compare(this.moduleVersion, other.moduleVersion) * -1
    }

}
