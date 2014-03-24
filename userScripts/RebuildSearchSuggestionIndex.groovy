import grails.plugin.searchable.SearchableService

/**
 * Created by herooftime on 3/21/14.
 */


SearchableService searchableService = ctx.getBean("searchableService")

println("Rebuilding spelling suggestion index...")
searchableService.rebuildSpellingSuggestions()

println("Index rebuild complete.")