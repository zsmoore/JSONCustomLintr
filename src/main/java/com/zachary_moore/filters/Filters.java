package com.zachary_moore.filters;

import com.zachary_moore.objects.JSONArray;
import com.zachary_moore.objects.JSONFile;
import com.zachary_moore.objects.JSONObject;
import com.zachary_moore.objects.WrappedPrimitive;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Intermediate Filter class to handle type casting
 */
class Filters extends BaseFilter {

    ArrayList<JSONObject> filterToObjects(JSONFile file) {
        return (ArrayList<JSONObject>) getAllOfType(file, JSONObject.class);
    }

    ArrayList<JSONArray> filterToArrays(JSONFile file) {
        return (ArrayList<JSONArray>) getAllOfType(file, JSONArray.class);
    }

    ArrayList<WrappedPrimitive<String>> filterToStrings(JSONFile file) {
        return (ArrayList<WrappedPrimitive<String>>) getAllOfWrappedType(file, String.class);
    }

    ArrayList<WrappedPrimitive<Byte>> filterToBytes(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Byte>>) getAllOfWrappedType(file, Byte.class);
    }

    ArrayList<WrappedPrimitive<Character>> filterToCharacters(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Character>>) getAllOfWrappedType(file, Character.class);
    }

    ArrayList<WrappedPrimitive<Short>> filterToShorts(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Short>>) getAllOfWrappedType(file, Short.class);
    }

    ArrayList<WrappedPrimitive<Integer>> filterToIntegers(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Integer>>) getAllOfWrappedType(file, Integer.class);
    }

    ArrayList<WrappedPrimitive<Long>> filterToLongs(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Long>>) getAllOfWrappedType(file, Long.class);
    }

    ArrayList<WrappedPrimitive<Boolean>> filterToBooleans(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Boolean>>) getAllOfWrappedType(file, Boolean.class);
    }

    ArrayList<WrappedPrimitive<Float>> filterToFloats(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Float>>) getAllOfWrappedType(file, Float.class);
    }

    ArrayList<WrappedPrimitive<Double>> filterToDoubles(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Double>>) getAllOfWrappedType(file, Double.class);
    }

    ArrayList<WrappedPrimitive<BigInteger>> filterToBigIntegers(JSONFile file) {
        return (ArrayList<WrappedPrimitive<BigInteger>>) getAllOfWrappedType(file, BigInteger.class);
    }

    ArrayList<WrappedPrimitive<BigDecimal>> filterToBigDecimals(JSONFile file) {
        return (ArrayList<WrappedPrimitive<BigDecimal>>) getAllOfWrappedType(file, BigDecimal.class);
    }

    ArrayList<WrappedPrimitive<Enum>> filterToEnums(JSONFile file) {
        return (ArrayList<WrappedPrimitive<Enum>>) getAllOfWrappedType(file, Enum.class);
    }
}
