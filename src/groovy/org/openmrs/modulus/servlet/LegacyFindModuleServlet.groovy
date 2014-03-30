package org.openmrs.modulus.servlet

import grails.plugin.searchable.SearchableService
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware
import org.hibernate.Session
import org.hibernate.SessionFactory

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.openmrs.modulus.Module

/**
 * Created by Elliott Williams on 3/28/14.
 * Based on org.openmrs.modulerepository.servlet.FindModulesServlet
 *
 * Mapped to the url "/findModules". Can take in an optional "sSearch" parameter
 * that will match to a module's name, id, or description.
 * <br/><br/>
 * Returns jquery style json for use in a datatable.
 * <br/>
 * <ul>
 * <li>sSearch = the query to search on
 * <li>iDisplayStart = start number, default 0 - http://www.datatables.net/usage/server-side
 * <li>iDisplayLength = number to return, default 100 - http://www.datatables.net/usage/server-side
 * <li>iSortingCols = 1, 2, 3, 4 for name, version, author, description
 * <li>iSortCol_ =
 * <li>iSortDir_ = asc/desc
 * <li>sEcho = echoed back in json
 * <li>excludeModule = (can be used multiple times) string module ids to ignore
 * <li>openmrs_version = current openmrs version used for compatibility
 * </ul>
 */
class LegacyFindModule implements GrailsApplicationAware {
    public static grailsApplication

    public void setGrailsApplication(GrailsApplication app) {
        grailsApplication = app
    }

}

class LegacyFindModuleServlet extends HttpServlet {

    GrailsApplication grailsApplication;
    Class<Module> module;
    SearchableService searchableService;
    SessionFactory sessionFactory;

    LegacyFindModuleServlet() {
        grailsApplication = LegacyFindModule.grailsApplication;
        module = LegacyFindModule.grailsApplication.getClassForName("org.openmrs.modulus.Module");
        searchableService = LegacyFindModule.grailsApplication.getMainContext().getBean("searchableService");
        sessionFactory = LegacyFindModule.grailsApplication.getMainContext().getBean("sessionFactory");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        request.getServletContext()


        response.setContentType("text/json");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        final int iTotalRecords;

        // the following parameters are described in http://www.datatables.net/usage/server-side
        final int iDisplayStart = getIntParameter(request, "iDisplayStart", 0);
        final int iDisplayLength = getIntParameter(request, "iDisplayLength",
                100);
        final String sSearch = request.getParameter("sSearch");

        if (!"".equals(sSearch)) {
            System.out.println("Module search for : " + sSearch);
        }


        // final boolean bEscapeRegex =
        // "true".equalsIgnoreCase(request.getParameter("bEscapeRegex"));
        // final int iColumns = getIntParameter(request, "iColumns", 0);
        final int iSortingCols = getIntParameter(request, "iSortingCols", 0);
        final int[] sortingCols = new int[iSortingCols];
        final String[] sortingDirs = new String[iSortingCols];
        for (int i = 0; i < iSortingCols; i++) {
            final int iSortCol = getIntParameter(request, "iSortCol_" + i, 0);
            final String iSortDir = request.getParameter("iSortDir_" + i);
            sortingCols[i] = iSortCol;
            sortingDirs[i] = iSortDir;
        }
        final String sEcho = request.getParameter("sEcho");

        final String[] excludeModules = request
                .getParameterValues("excludeModule");
        List<String> excludeMods = excludeModules != null ? Arrays
                .asList(excludeModules) : new ArrayList<String>();
        final String openmrsVersion = request.getParameter("openmrs_version");


        List<Module> modules = findModules(sSearch, openmrsVersion,
                excludeMods, sortingCols, sortingDirs);

        iTotalRecords = module.list().size();


        final int iTotalDisplayRecords = modules.size();
        int fromIndex = iDisplayStart;
        int toIndex = fromIndex + iDisplayLength;
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (toIndex > iTotalDisplayRecords) {
            toIndex = iTotalDisplayRecords;
        }
        if (fromIndex > toIndex) {
            int aux = toIndex;
            toIndex = fromIndex;
            fromIndex = aux;
        }
        modules = modules.subList(fromIndex, toIndex);

//        System.out.println("searching for : " + sSearch + " and excludeModules: " + excludeModules + " and openmrs v: " + openmrsVersion);

        final String jsonpcallback = request.getParameter("callback");

        // to support cross site scripting and jquery's jsonp
        if (jsonpcallback != null)
            out.print(jsonpcallback + "(");

        out.print("{");
        try {
            int sEchoVal = Integer.valueOf(sEcho);
            out.print("\"sEcho\":" + sEchoVal + ",");
        } catch (NumberFormatException nfe) {}
        out.print("\"iTotalRecords\":" + iTotalRecords + ",");
        out.print("\"iTotalDisplayRecords\":" + iTotalDisplayRecords + ",");
//        out.print("\"sColumns\": \"Action,Name,Version,Author,Description\",");
        out.print("\"sColumns\": \"Action,Name,Version,Description\",");
        out.print("\"aaData\":");
        out.print("[");
        boolean first = true;

        for (Module resultModule : modules) {
            if (first) {
                first = false;
            } else {
                out.print(",");
            }
            out.print("[");
            out.print("\"" + resultModule.releases.last().getDownloadURL() + "\",");
            out.print("\"" + resultModule.getName() + "\",");
            out.print("\"" + resultModule.releases.last().getVersion() + "\",");
//            out.print("\"" + resultModule.getAuthor() + "\",");
            String description = resultModule.getDescription();
            if (description.length() > 200) {
                description = description.substring(0, 200) + "...";
            }
            out.print("\"" + LegacyFindModuleUtil.escape(description) + "\"");
            out.print("]");
        }
        out.print("]");
        out.print("}");

        // to support cross site scripting and jquery's jsonp
        if (jsonpcallback != null)
            out.print(")");

    }

    private int getIntParameter(HttpServletRequest request, String param,
                                int defaultVal) {
        try {
            return Integer.valueOf(request.getParameter(param));
        } catch (NumberFormatException nfe) {
            return defaultVal;
        }
    }

    /**
     * Find a list of modules from Modulus using this legacy method.
     * @param search search query
     * @param openmrsVersion version of openmrs being queried from
     * @param excludeMods do not return modules in this list
     * @param sortingCols order of requested columns
     * @param sortingDirs direction of requested columns
     * @return List of Module instances matching search parameters
     */
    private List<Module> findModules(String search, String openmrsVersion, Collection<String> excludeMods,
                                     int[] sortingCols, String[] sortingDirs) {

        if (!search) {
            return [];
        }


        // Run the search query.
        List<Module> results = searchableService.search(search, defaultProperty: "name").results;

        // Build a list of Module objects that have been called to exclude.
        Collection<Module> modulesToExclude = [];
        excludeMods.each { String exId->
            def mod = module.findWhere(legacyId: exId)
            if (mod) {
                modulesToExclude.add(mod)
            }
        }

        // Get additional properties from the DB, and connect each object to the current Hibernate session.
        results.each { Module entry ->
            entry.refresh()
        }

        // Remove all excluded modules from the search result.
        results.removeAll(excludeMods)

        // Remove any module that has no releases.
        results.eachWithIndex { Module entry, int i ->
            if (!entry.releases || entry.releases.size() == 0) {
                results.remove(i)
            }
        }

        // Close the session & return
        return results


    }
}

class LegacyFindModuleUtil {
    /** copied from http://json-simple.googlecode.com/svn/trunk/src/org/json/simple/JSONValue.java Revision 184
     *
     * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters
     * (U+0000 through U+001F).
     *
     * @param s
     * @return
     */
    public static String escape(String s) {
        if (s == null)
            return null;
        StringBuffer sb = new StringBuffer();
        escape(s, sb);
        return sb.toString();
    }

    /**
     * @param s
     *            - Must not be null.
     * @param sb
     */
    public static void escape(String s, StringBuffer sb) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    // Reference: http://www.unicode.org/versions/Unicode5.1.0/
                    if ((ch >= '\u0000' && ch <= '\u001F')
                            || (ch >= '\u007F' && ch <= '\u009F')
                            || (ch >= '\u2000' && ch <= '\u20FF')) {
                        String ss = Integer.toHexString(ch);
                        sb.append("\\u");
                        for (int k = 0; k < 4 - ss.length(); k++) {
                            sb.append('0');
                        }
                        sb.append(ss.toUpperCase());
                    } else {
                        sb.append(ch);
                    }
            }
        }// for
    }
}
