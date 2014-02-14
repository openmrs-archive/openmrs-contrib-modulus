package org.openmrs.modulus.admin

import org.openmrs.modulus.Screenshot

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AdminScreenshotController {
    static scaffold = Screenshot

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Screenshot.list(params), model:[screenshotInstanceCount: Screenshot.count()]
    }

    def show(Screenshot screenshotInstance) {
        respond screenshotInstance
    }

    def create() {
        respond new Screenshot(params)
    }

    @Transactional
    def save(Screenshot screenshotInstance) {
        if (screenshotInstance == null) {
            notFound()
            return
        }

        if (screenshotInstance.hasErrors()) {
            respond screenshotInstance.errors, view:'create'
            return
        }

        screenshotInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'screenshotInstance.label', default: 'Screenshot'), screenshotInstance.id])
                redirect screenshotInstance
            }
            '*' { respond screenshotInstance, [status: CREATED] }
        }
    }

    def edit(Screenshot screenshotInstance) {
        respond screenshotInstance
    }

    @Transactional
    def update(Screenshot screenshotInstance) {
        if (screenshotInstance == null) {
            notFound()
            return
        }

        if (screenshotInstance.hasErrors()) {
            respond screenshotInstance.errors, view:'edit'
            return
        }

        screenshotInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Screenshot.label', default: 'Screenshot'), screenshotInstance.id])
                redirect screenshotInstance
            }
            '*'{ respond screenshotInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Screenshot screenshotInstance) {

        if (screenshotInstance == null) {
            notFound()
            return
        }

        screenshotInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Screenshot.label', default: 'Screenshot'), screenshotInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'screenshotInstance.label', default: 'Screenshot'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
