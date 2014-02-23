package org.openmrs.modulus

import org.openmrs.modulus.models.Completable

class Module implements Completable {

    def slugGeneratorService

    String name
    String description
    String documentationURL

    // Auto-generated:
    String slug
    Boolean complete

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
        documentationURL url: true, nullable: true
        slug maxLength: 255, nullable: true
        complete nullable: false
    }

    def beforeInsert() {
        if (this.name) {
            updateSlug()
        }
    }

    def beforeValidate() {
        this.complete = completed()
    }

    def beforeUpdate() {
        if (isDirty('name')) {
            updateSlug()
        }
    }

    private def updateSlug() {
        def slug = slugGeneratorService.generateSlug(this.class, "slug", this.name)
        this.slug = slug
    }

    boolean completed() {
        this.name && this.description && this.releases?.size() > 0
    }
}
