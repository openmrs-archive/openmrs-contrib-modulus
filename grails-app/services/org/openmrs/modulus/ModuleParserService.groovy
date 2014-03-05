package org.openmrs.modulus

import grails.transaction.Transactional
//import org.openmrs.module.ModuleFileParser

@Transactional
class ModuleParserService {

    /**
     * Parse metadata within a module file
     * @param moduleFile Reference to an OMOD file
     * @return map of module metadata relevant to the Module Repository data model. can be
     */
    def parse(File moduleFile) {
        // TODO get the OpenMRS API running as an app dependency
        /*def parser = new ModuleFileParser(moduleFile)
        def mod = parser.parse()

        return [
                module: [
                        name: mod.getName(),
                        description: mod.getDescription(),
                ],
                requiredOMRSVersion: mod.getRequireOpenmrsVersion(),
                moduleVersion: mod.getVersion()
        ]*/
        return []
    }

    /**
     * Convenience method if only the path to a file is available
     * @param moduleFilePath path to OMOD
     */
    def parse(String moduleFilePath) {
        parse(new File(moduleFilePath))
    }
}
