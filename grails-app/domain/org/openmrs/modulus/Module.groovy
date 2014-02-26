package org.openmrs.modulus

class Module {

    def slugGeneratorService

    String name
    String description
    String documentationURL

    // Auto-generated:
    String slug

    Date dateCreated
    Date lastUpdated

    static hasMany = [releases:Release,
            screenshots:Screenshot]

    static mapping = {
        autoTimestamp true
    }

    static constraints = {
        name maxLength: 100, nullable: true
        description maxLength: 10000, nullable: true
        documentationURL nullable: true, url: true
        slug maxLength: 255, nullable: true
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
        this.slug = slugGeneratorService.generateSlug(this.class, "slug", name)
    }
}
