package detectors;

import objects.JSONArray;
import objects.JSONFile;
import objects.JSONObject;
import objects.WrappedPrimitive;

import java.util.ArrayList;

public class Filters extends BaseFilter {

    public ArrayList<JSONObject> filterToObjects(JSONFile file) {
        return (ArrayList<JSONObject>) getAllOfType(file, JSONObject.class);
    }

    public ArrayList<JSONArray> filterToArrays(JSONFile file) {
        return (ArrayList<JSONArray>) getAllOfType(file, JSONArray.class);
    }

    public ArrayList<WrappedPrimitive<String>> filterToStrings(JSONFile file) {
        return (ArrayList<WrappedPrimitive<String>>) getAllOfWrappedType(file, String.class);
    }
}
