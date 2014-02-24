package org.openmrs.modulus

import org.openmrs.modulus.models.Completable

class Module implements Completable {

    def slugGeneratorService

    String name
    String description
    String documentationURL

    // Auto-generated:
    String slug
    Boolean completed

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
        completed nullable: true
    }

    def beforeInsert() {
        if (name) {
            updateSlug()
        }
    }

    def beforeValidate() {
        completed = completed()
        log.debug("completed=$completed")
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

    private def updateSlug() {
        def slug = slugGeneratorService.generateSlug(this.class, "slug", name)
        slug = slug
    }

    boolean completed() {
        log.debug("name=$name")
        log.debug("description=$description")
        log.debug("releases=$releases")
//        this.name && this.description && this.releases?.size() > 0
        return true
    }
}
