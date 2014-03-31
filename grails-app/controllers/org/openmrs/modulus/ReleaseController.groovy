package org.openmrs.modulus

import static org.springframework.http.HttpStatus.NOT_FOUND

class ReleaseController extends RestfulUploadController {
    static responseFormats = ['json', 'xml']
    ReleaseController() {
        super(Release)
    }

    def moduleParserService

    /**
     * Send a not found error if a module id is passed that doesn't exist.
     */
    def beforeInterceptor = {
        // Include the parent module
        if (params.moduleId) {
            params.module = [id: params.moduleId]

            if (!Module.exists(params.moduleId)) {
                render text: "Module ID ${params.moduleId} not found", status: NOT_FOUND
                return false
            }
        }
    }

    /**
     * Parse module file metadata in addition to doing the upload.
     * @param instance
     * @return
     */
    @Override
    protected doUpload(Release instance) {
        super.doUpload(instance)

        def meta = moduleParserService.parse(instance.path)
        instance.properties.plus(meta)
        // instance will be saved at the end of the request
    }

    /**
     * Download release and increment its downloadCount
     * @param id id of release
     * @return
     */
    @Override
    def download(Integer id) {
        super.download(id)
        withInstance id, { Release instance ->
            instance.incrementDownloadCount()
            instance.save()
        }
    }

    /**
     * Upload to an existing release, using the <code>releaseId</code> parameter if it's available
     */
    @Override
    def uploadExisting() {
        params.id = params.id ?: params.releaseId

        return super.uploadExisting()
    }

    @Override
    protected Map getParametersToBind() {
        params
    }

    /**
     * List all resources, optionally only those belonging to a specific module
     * @param params Parameters used to generate the list. If a <code>module</code> is one of the params,
     *               look up releases for that module only.
     */
    @Override
    protected List listAllResources(Map params) {
        if (params.module) {
            def module = Module.get(params.module.id)
            Release.findAllByModule(module, params)
        } else {
            Release.list(params)
        }
    }
}
