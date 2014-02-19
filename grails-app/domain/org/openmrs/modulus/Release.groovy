package org.openmrs.modulus

class Release extends Uploadable{

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
    }


    static constraints = {
        releasedBy nullable: true
        moduleVersion maxLength: 255
        requiredOMRSVersion maxLength: 255, nullable: true
    }


    static def downloadLinkGeneratorService

    def beforeValidate() {
        if (this.filename) {
            this.downloadURL = downloadLinkGeneratorService.URL("release", this.id, this.filename)
        }
    }

    def beforeUpdate() {
        if (isDirty('filename') && this.filename) {
            this.downloadURL = downloadLinkGeneratorService.URL("release", this.id, this.filename)
        }
    }
}
