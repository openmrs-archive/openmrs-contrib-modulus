package org.openmrs.modulus

import grails.rest.RestfulController

class ScreenshotController extends RestfulUploadController {
    static responseFormats = ['json', 'xml']
    ScreenshotController() {
        super(Screenshot)
    }
}
