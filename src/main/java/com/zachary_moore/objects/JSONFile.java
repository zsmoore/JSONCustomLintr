package com.zachary_moore.objects;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONFile implements WrappedObject {

    private JSONObject wrappedJsonObject;
    private JSONArray wrappedJsonArray;
    private String filePath;
    private String fileExtension;
    private List<String> linesInFile;

    public JSONFile(File file) throws IOException {
        filePath = file.getCanonicalPath();
        fileExtension = FilenameUtils.getExtension(filePath);
        initializeLineNumberData(file);
        initializeWrappedObjects(file);
    }

    private void initializeWrappedObjects(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        JSONTokener jsonTokener = new JSONTokener(bufferedReader);
        try {
            this.wrappedJsonObject = new JSONObject(null,
                    this,
                    new org.json.JSONObject(jsonTokener));
        } catch (JSONException e) {
            jsonTokener.back();
            this.wrappedJsonArray = new JSONArray(null,
                    this,
                    new org.json.JSONArray(jsonTokener));
        }
        bufferedReader.close();
    }

    /**
     * @return either {@link JSONObject} or {@link JSONArray} based on input file to constructor
     */
    public WrappedObject getChild() {
        return wrappedJsonObject != null ? wrappedJsonObject : wrappedJsonArray;
    }

    @Override
    public String getOriginatingKey() {
        return null;
    }

    @Override
    public WrappedObject getParentObject() {
        return null;
    }

    @Override
    public void parseAndReplaceWithWrappers() { }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    private void initializeLineNumberData(File file) throws IOException{
        linesInFile = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            linesInFile.add(line);
        }
    }

    public int getLineNumber(String offendingText) {
        for (int i = 0; i < linesInFile.size(); i++) {
            if (linesInFile.get(i).contains(offendingText)) {
                return i + 1;
            }
        }
        return -1;
    }

    @Override
    public int hashCode() {
        return this.filePath.hashCode();
    }
}
