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

    static embedded = ['releases', 'screenshots']

    static mapping = {
        autoTimestamp true
    }

    static constraints = {
        name maxLength: 100, nullable: true
        description maxLength: 10000, nullable: true
        documentationURL url: true, nullable: true
        slug maxLength: 255, nullable: true
    }

    def beforeInsert() {
        def slug = slugGeneratorService.generateSlug(this.class, "slug", this.name)
        this.slug = slug
    }

    def beforeUpdate() {
        if (isDirty('name')) {
            this.slug = slugGeneratorService.generateSlug(this.class, "slug", this.name)
        }
    }

}
