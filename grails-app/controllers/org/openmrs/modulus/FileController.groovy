package org.openmrs.modulus

import grails.rest.RestfulController
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Deprecated
class FileController extends RestfulUploadController {
    static responseFormats = ['json', 'xml']


    FileController() {
        super(File)
    }
}
