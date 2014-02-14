package org.openmrs.modulus.admin

import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AdminFileController {
    static scaffold = org.openmrs.modulus.File

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond org.openmrs.modulus.File.list(params), model:[fileInstanceCount: org.openmrs.modulus.File.count()]
    }

    def show(org.openmrs.modulus.File fileInstance) {
        respond fileInstance
    }

    def create() {
        respond new org.openmrs.modulus.File(params)
    }

    def download(Integer id) {
        org.openmrs.modulus.File f = org.openmrs.modulus.File.get(id)
        if (f == null) {
            notFound()
            return
        }

        byte[] contents = f.rawFile
        render file: contents, fileName: f.filename, contentType: f.contentType
    }

    @Transactional
    def save(org.openmrs.modulus.File fileInstance) {
        if (fileInstance == null) {
            notFound()
            return
        }

        setFileMetadata(fileInstance)

        if (fileInstance.hasErrors()) {
            respond fileInstance.errors, view:'create'
            return
        }

        fileInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'fileInstance.label', default: 'File'), fileInstance.id])
                redirect fileInstance
            }
            '*' { respond fileInstance, [status: CREATED] }
        }
    }

    def edit(org.openmrs.modulus.File fileInstance) {
        respond fileInstance
    }

    @Transactional
    def update(org.openmrs.modulus.File fileInstance) {
        if (fileInstance == null) {
            notFound()
            return
        }

        setFileMetadata(fileInstance)

        if (fileInstance.hasErrors()) {
            respond fileInstance.errors, view:'edit'
            return
        }

        fileInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'File.label', default: 'File'), fileInstance.id])
                redirect fileInstance
            }
            '*'{ respond fileInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(org.openmrs.modulus.File fileInstance) {

        if (fileInstance == null) {
            notFound()
            return
        }

        fileInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'File.label', default: 'File'), fileInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'fileInstance.label', default: 'File'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    protected void setFileMetadata(org.openmrs.modulus.File fileInstance) {
        MultipartFile f = request.getFile('rawFile')
        if (!f.isEmpty()) {
            fileInstance.filename = f.getOriginalFilename()
            fileInstance.contentType = f.getContentType()
            fileInstance.save()
        }
    }
}
