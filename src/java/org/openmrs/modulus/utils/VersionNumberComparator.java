package org.openmrs.modulus.utils;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * This comparator is meant to be used to sort module version numbers.
 * For example, given keys "1.A", "1.B", and "10.A", this should sort them by level, first numerically if possible,
 * and then alpha-numerically otherwise.  The keys above, compared strictly by their natural order as Strings,
 * would sort as "1.A", "10.A", "1.B".  This comparator ensures that "1.B" is sorted higher than "10.A".
 */
public class VersionNumberComparator implements Comparator<String> {

	// *******************
	// Constructor
	// *******************
	public VersionNumberComparator() {}
	
	// *******************
	// Properties
	// *******************
    private static final log = LogFactory.getLog(this)

	// *******************
	// Instance methods
	// *******************
	
	/**
	 * @see java.util.Comparator#compare(Object, Object)
	 */
    public int compare(String version, String value) {
        try {
            if (version == null || value == null) {
                return 0;
            }

            List<String> versions = new Vector<String>();
            List<String> values = new Vector<String>();
            String separator = "-";

            // strip off any qualifier e.g. "-SNAPSHOT"
            int qualifierIndex = version.indexOf(separator);
            if (qualifierIndex != -1) {
                version = version.substring(0, qualifierIndex);
            }

            qualifierIndex = value.indexOf(separator);
            if (qualifierIndex != -1) {
                value = value.substring(0, qualifierIndex);
            }

            Collections.addAll(versions, version.split("\\."));
            Collections.addAll(values, value.split("\\."));

            // match the sizes of the lists
            while (versions.size() < values.size()) {
                versions.add("0");
            }
            while (values.size() < versions.size()) {
                values.add("0");
            }

            for (int x = 0; x < versions.size(); x++) {
                String verNum = versions.get(x).trim();
                String valNum = values.get(x).trim();
                Integer ver = NumberUtils.toInt(verNum, 0);
                Integer val = NumberUtils.toInt(valNum, 0);

                int ret = ver.compareTo(val);
                if (ret != 0) {
                    return ret;
                }
            }
        }
        catch (NumberFormatException e) {
            log.error("Error while converting a version/value to an integer: " + version + "/" + value, e);
        }

        // default return value if an error occurs or elements are equal
        return 0;
    }
//	public int compare(String s1, String s2) {
//
//		if (s1 == s2) {return 0;}
//		if (s1 == null) {return -1;}
//		if (s2 == null) {return 1;}
//
//		String[] s1Split = regex.split(s1);
//		String[] s2Split = regex.split(s2);
//
//		int maxInCommon = (s1Split.length > s2Split.length ? s2Split.length : s1Split.length);
//
//		// Compare each common level
//		for (int i=0; i<maxInCommon; i++) {
//			String sub1 = s1Split[i];
//			String sub2 = s2Split[i];
//			int currentRes = 0;
//			try {
//				Integer i1 = Integer.valueOf(sub1);
//				Integer i2 = Integer.valueOf(sub2);
//				currentRes = i1.compareTo(i2);
//			}
//			catch (NumberFormatException nfe) {
//				currentRes = sub1.compareTo(sub2);
//			}
//			if (currentRes != 0) { return currentRes; }
//		}
//
//		// If all common levels are the same, one with least number of sub-levels is greater
//		if (s1Split.length > s2Split.length) {
//			return 1;
//		}
//		if (s2Split.length > s2Split.length) {
//			return -1;
//		}
//		return 0;
//	}
}
