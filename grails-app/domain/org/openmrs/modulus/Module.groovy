package org.openmrs.modulus

class Module {
    static searchable = true

    def slugGeneratorService

    String name
    String description
    String documentationURL

    String legacyId

    // Auto-generated:
    String slug

    Date dateCreated
    Date lastUpdated

    static hasMany = [releases:Release,
            screenshots:Screenshot]
    List releases
    List screenshots

    static mapping = {
        autoTimestamp true
    }

    static constraints = {
        name maxLength: 100, nullable: true
        description maxLength: 10000, nullable: true
        documentationURL nullable: true, url: true
        slug maxLength: 255, nullable: true
        legacyId nullable: true
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
