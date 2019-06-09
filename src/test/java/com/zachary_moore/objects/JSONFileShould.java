package com.zachary_moore.objects;


import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class JSONFileShould {

    private File jsonObjectFile;
    private File jsonArrayFile;

    public JSONFileShould() {
        jsonObjectFile = new File(getClass()
            .getClassLoader()
            .getResource("test-2.json")
            .getFile());
        jsonArrayFile = new File(getClass()
            .getClassLoader()
            .getResource("array-file.json")
            .getFile());
    }

    @Test
    public void JSONFileGivesJSONArray() throws IOException {
        JSONFile jsonFile = new JSONFile(jsonArrayFile);
        assert(jsonFile.getChild() instanceof JSONArray);
        assert(((JSONArray)jsonFile.getChild()).length() == 3);
    }

    @Test
    public void JSONFileGivesJSONObject() throws IOException {
        JSONFile jsonFile = new JSONFile(jsonObjectFile);
        assert(jsonFile.getChild() instanceof JSONObject);
    }

    @Test
    public void JSONFileGivesFilePath() throws IOException {
        JSONFile jsonFile = new JSONFile(jsonObjectFile);
        assert(jsonFile.getFilePath().equals(jsonObjectFile.getAbsolutePath()));
    }

    @Test
    public void JSONFileGivesFileExtension() throws IOException {
        JSONFile jsonFile = new JSONFile(jsonObjectFile);
        assert(jsonFile.getFileExtension().equals("json"));
    }

    @Test
    public void JSONFileChildShouldHaveParent() throws IOException {
        JSONFile jsonFile = new JSONFile(jsonObjectFile);
        assertNotNull(jsonFile.getChild().getParentObject());
    }
}
