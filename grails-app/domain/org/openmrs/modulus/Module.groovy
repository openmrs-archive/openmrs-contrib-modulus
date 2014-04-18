package org.openmrs.modulus

import org.openmrs.modulus.utils.VersionNumberComparator

class Module {
    static searchable = {
        name spellCheck: "include", boost: 2.0
        description spellCheck: "include"
    }

    def slugGeneratorService

    String name
    String description
    String documentationURL

    String legacyId

    Integer downloadCount = 0

    def incrementDownloadCount() {
        this.downloadCount++
        return this.downloadCount
    }

    // Auto-generated:
    String slug

    Date dateCreated
    Date lastUpdated

    static hasMany = [releases: Release,
                      screenshots: Screenshot,
                      maintainers: User]

    SortedSet<Release> releases
    List screenshots

    User owner

    static mapping = {
        autoTimestamp true
    }

    static constraints = {
        name maxSize: 100, nullable: true
        description maxSize: 10000, nullable: true
        documentationURL nullable: true, url: true
        slug maxSize: 255, nullable: true
        legacyId nullable: true
        maintainers nullable: true
        owner nullable: false
    }

    static marshalling = {
        deep 'owner', 'maintainers'
    }




    def beforeInsert() {
        if (this.name) {
            updateSlug()
        }
    }


    def beforeUpdate() {
        if (isDirty('name')) {
            if (name) {
                updateSlug()
            } else {
                slug = null
            }
        }
    }

    public def updateSlug() {
        // TODO: slugs should not clash with legacyIds
        this.slug = slugGeneratorService.generateSlug(this.class, "slug", name)
    }
}
