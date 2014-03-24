import org.openmrs.modulus.Module
import org.openmrs.modulus.Release

/**
 * Created by herooftime on 3/21/14.
 */

println("Updating all module download counts")

def totalDownloads = 0
def totalModules = 0

Module.all.each {Module mod ->

    totalModules++

    def dc = 0
    mod.releases.each {Release rel ->
        dc += rel.downloadCount
    }
    totalDownloads += dc
    mod.downloadCount = dc
    mod.save()
}

println("Counted $totalDownloads downloads over $totalModules modules")