import groovy.io.FileType
import org.openmrs.modulus.*;

def UPLOADS = System.getProperty('modulus.migratedir') ?: './modulerepository/uploads'

def uploadableService = ctx.getBean("uploadableService")


def version = { String input, String moduleId ->
    def version = input.toLowerCase()
    version = version.replaceFirst("${moduleId}-", "")
    return version.replaceFirst(".omod", "")
}

def uploadToRelease = { Release rel, File file ->

    uploadableService.uploadFile(rel, file.bytes, file.name)
    rel.save()

    println("Uploaded ${file.name} to ${rel.module.legacyId}:${rel.moduleVersion}")
}


def loadReleases = { Module mod, File dir ->
    dir.eachFile { file ->
        if (file.name.endsWith('.omod')) {

            def v = version(file.name, mod.legacyId)

            def rel = Release.findWhere([module: mod, moduleVersion: v])
            if (rel) {
                uploadToRelease(rel, file)
            } else {
                println("WARNING: No match found for ${mod.legacyId}:${v}")
            }
        }
    }
}

def loadModules = {
    new File(UPLOADS).eachDir { File subdir ->

        def mod = Module.findByLegacyId(subdir.name)

        if (mod) {
            loadReleases(mod, subdir)
        }

    }
}

def printSizes = {
    def filesLength = new File(UPLOADS).listFiles().length
    println("$filesLength modules in $UPLOADS")
    println("${Module.count()} modules in Modulus")
}


def main = {

    println("Looking for omods in $UPLOADS...")

    printSizes()

    loadModules()

    println("Upload process complete")
}

main()