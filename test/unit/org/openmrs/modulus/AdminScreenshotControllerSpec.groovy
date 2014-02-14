package org.openmrs.modulus



import grails.test.mixin.*
import org.openmrs.modulus.admin.AdminScreenshotController
import spock.lang.*

@TestFor(AdminScreenshotController)
@Mock(Screenshot)
class AdminScreenshotControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.screenshotInstanceList
            model.screenshotInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.screenshotInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def screenshot = new Screenshot()
            screenshot.validate()
            controller.save(screenshot)

        then:"The create view is rendered again with the correct model"
            model.screenshotInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            screenshot = new Screenshot(params)

            controller.save(screenshot)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/screenshot/show/1'
            controller.flash.message != null
            Screenshot.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def screenshot = new Screenshot(params)
            controller.show(screenshot)

        then:"A model is populated containing the domain instance"
            model.screenshotInstance == screenshot
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def screenshot = new Screenshot(params)
            controller.edit(screenshot)

        then:"A model is populated containing the domain instance"
            model.screenshotInstance == screenshot
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/screenshot/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def screenshot = new Screenshot()
            screenshot.validate()
            controller.update(screenshot)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.screenshotInstance == screenshot

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            screenshot = new Screenshot(params).save(flush: true)
            controller.update(screenshot)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/screenshot/show/$screenshot.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/screenshot/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def screenshot = new Screenshot(params).save(flush: true)

        then:"It exists"
            Screenshot.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(screenshot)

        then:"The instance is deleted"
            Screenshot.count() == 0
            response.redirectedUrl == '/screenshot/index'
            flash.message != null
    }
}
