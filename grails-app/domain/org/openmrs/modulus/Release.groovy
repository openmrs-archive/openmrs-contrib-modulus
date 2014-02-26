package org.openmrs.modulus

import org.openmrs.modulus.models.Completable

class Release extends Uploadable {

    String moduleVersion
    String requiredOMRSVersion

    User releasedBy

    // Auto-generated:
    Date dateCreated
    Date lastUpdated

    static belongsTo = [module: Module]



    static mapping = {
        table '`release`' // Release is a MySQL reserved word; backticks force Hibernate to escape it
        autoTimestamp true
        tablePerHierarchy false
    }


    static constraints = {
        releasedBy nullable: true
        moduleVersion maxLength: 255, nullable: true
        requiredOMRSVersion maxLength: 255, nullable: true
    }


    static def downloadLinkGeneratorService

    def afterInsert() {
        if (this.filename) {
            this.downloadURL = downloadLinkGeneratorService.URL("release", this.id, this.filename)
            this.save()
        }
    }


    def beforeUpdate() {
        if (isDirty('filename') && this.filename) {
            this.downloadURL = downloadLinkGeneratorService.URL("release", this.id, this.filename)
        }
    }

}
