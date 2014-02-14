package org.openmrs.modulus

class Module {

    def slugGeneratorService

    String name
    String slug
    String description
    String documentationURL

    Date dateCreated
    Date lastUpdated

    static hasMany = [releases:Release,
            screenshots:Screenshot]

    static mapping = {
        autoTimestamp true
    }

    static constraints = {
        name maxLength: 100
        slug maxLength: 255
        description maxLength: 10000
        documentationURL url: true
    }

    def beforeValidate() {
        def slug = slugGeneratorService.generateSlug(this.class, "slug", name)
        this.slug = slug
    }

    def beforeUpdate() {
        if (isDirty('name')) {
            this.slug = slugGeneratorService.generateSlug(this.class, "slug", name)
        }
    }

}
