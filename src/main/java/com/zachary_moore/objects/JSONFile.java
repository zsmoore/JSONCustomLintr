package com.zachary_moore.objects;

import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONFile {

    private org.json.JSONObject jsonObject;
    private org.json.JSONArray jsonArray;
    private String filePath;

    public JSONFile(File file) throws IOException {
        filePath = file.getCanonicalPath();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        JSONTokener jsonTokener = new JSONTokener(bufferedReader);
        try {
            jsonObject = new org.json.JSONObject(jsonTokener);
        } catch (JSONException e) {
            jsonTokener.back();
            jsonArray = new org.json.JSONArray(jsonTokener);
        }
        bufferedReader.close();
    }

    /**
     * @return either {@link JSONObject} or {@link JSONArray} based on input file to constructor
     */
    public Object getObject() {
        if (jsonObject != null) {
            return new JSONObject(null, null, jsonObject);
        } else if (jsonArray != null) {
            return new JSONArray(null, null, jsonArray);
        } else {
            throw new RuntimeException("Could not parse either a JSONArray or JSONObject from file");
        }
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public int hashCode() {
        return this.filePath.hashCode();
    }
}
