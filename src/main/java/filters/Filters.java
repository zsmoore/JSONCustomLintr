package filters;

import objects.JSONArray;
import objects.JSONFile;
import objects.JSONObject;
import objects.WrappedPrimitive;

import java.math.BigDecimal;
import java.math.BigInteger;
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

    public ArrayList<WrappedPrimitive<Byte>> filterToBytes(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Byte>>) getAllOfWrappedType(file, Byte.class);
    }

    public ArrayList<WrappedPrimitive<Character>> filterToCharacters(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Character>>) getAllOfWrappedType(file, Character.class);
    }

    public ArrayList<WrappedPrimitive<Short>> filterToShorts(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Short>>) getAllOfWrappedType(file, Short.class);
    }

    public ArrayList<WrappedPrimitive<Integer>> filterToIntegers(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Integer>>) getAllOfWrappedType(file, Integer.class);
    }

    public ArrayList<WrappedPrimitive<Long>> filterToLongs(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Long>>) getAllOfWrappedType(file, Long.class);
    }

    public ArrayList<WrappedPrimitive<Boolean>> filterToBooleans(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Boolean>>) getAllOfWrappedType(file, Boolean.class);
    }

    public ArrayList<WrappedPrimitive<Float>> filterToFloats(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Float>>) getAllOfWrappedType(file, Float.class);
    }

    public ArrayList<WrappedPrimitive<Double>> filterToDoubles(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Double>>) getAllOfWrappedType(file, Double.class);
    }

    public ArrayList<WrappedPrimitive<BigInteger>> filterToBigIntegers(JSONFile file) {
        return (ArrayList<WrappedPrimitive<BigInteger>>) getAllOfWrappedType(file, BigInteger.class);
    }

    public ArrayList<WrappedPrimitive<BigDecimal>> filterToBigDecimals(JSONFile file) {
        return (ArrayList<WrappedPrimitive<BigDecimal>>) getAllOfWrappedType(file, BigDecimal.class);
    }

    public ArrayList<WrappedPrimitive<Enum>> filterToEnums(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Enum>>) getAllOfWrappedType(file, Enum.class);
    }
}
