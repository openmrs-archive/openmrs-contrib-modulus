package org.openmrs.modulus

import org.openmrs.modulus.models.Completable

class Release extends Uploadable implements Completable{

    String moduleVersion
    String requiredOMRSVersion

    User releasedBy

    Boolean complete

    // Auto-generated:
    Date dateCreated
    Date lastUpdated

    static belongsTo = [module: Module]



    static mapping = {
        table '`release`' // Release is a MySQL reserved word; backticks force Hibernate to escape it
        autoTimestamp true
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

    def beforeValidate() {
        this.complete = completed()
    }

    def beforeUpdate() {
        if (isDirty('filename') && this.filename) {
            this.downloadURL = downloadLinkGeneratorService.URL("release", this.id, this.filename)
        }
    }

    boolean completed() {
        if (this.moduleVersion && this.module && (this.path || this.rawFile) && this.filename && this.contentType)
            true
        else
            false
    }
}
