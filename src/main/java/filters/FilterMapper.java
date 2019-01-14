package filters;

import lint.LintImplementation;
import objects.JSONArray;
import objects.JSONFile;
import objects.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class FilterMapper {

    public static List<?> filter(JSONFile jsonFile, LintImplementation lintImplementation) {
        Filters filters = new Filters();
        if (lintImplementation.getClazz() == String.class) {
            return filters.filterToStrings(jsonFile);
        } else if (lintImplementation.getClazz() == Byte.class) {
            return filters.filterToBytes(jsonFile);
        } else if (lintImplementation.getClazz() == Character.class) {
            return filters.filterToCharacters(jsonFile);
        } else if (lintImplementation.getClazz() == Short.class) {
            return filters.filterToShorts(jsonFile);
        } else if (lintImplementation.getClazz() == Integer.class) {
            return filters.filterToIntegers(jsonFile);
        } else if (lintImplementation.getClazz() == Long.class) {
            return filters.filterToLongs(jsonFile);
        } else if (lintImplementation.getClazz() == Boolean.class) {
            return filters.filterToBooleans(jsonFile);
        } else if (lintImplementation.getClazz() == Float.class) {
            return filters.filterToFloats(jsonFile);
        } else if (lintImplementation.getClazz() == Double.class) {
            return filters.filterToDoubles(jsonFile);
        } else if (lintImplementation.getClazz() == BigInteger.class) {
            return filters.filterToBigIntegers(jsonFile);
        } else if (lintImplementation.getClazz() == BigDecimal.class) {
            return filters.filterToBigDecimals(jsonFile);
        } else if (lintImplementation.getClazz() == Enum.class) {
            return filters.filterToEnums(jsonFile);
        } else if (lintImplementation.getClazz() == JSONObject.class) {
            return filters.filterToObjects(jsonFile);
        } else if (lintImplementation.getClazz() == JSONArray.class) {
            return filters.filterToArrays(jsonFile);
        } else {
            return null;
        }
    }
}
