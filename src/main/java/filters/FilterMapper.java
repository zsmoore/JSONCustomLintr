package filters;

import lint.LintImplementation;
import objects.JSONArray;
import objects.JSONFile;
import objects.JSONObject;

import java.util.List;

public class FilterMapper {

    //@SuppressWarnings("unchecked")
    public static List<?> filter(JSONFile jsonFile, LintImplementation lintImplementation) {
        Filters filters = new Filters();
        if (lintImplementation.getClazz() == String.class) {
            return filters.filterToStrings(jsonFile);
        } else if (lintImplementation.getClazz() == JSONObject.class) {
            return filters.filterToObjects(jsonFile);
        } else if (lintImplementation.getClazz() == JSONArray.class) {
            return filters.filterToArrays(jsonFile);
        } else {
            return null;
        }
    }
}
