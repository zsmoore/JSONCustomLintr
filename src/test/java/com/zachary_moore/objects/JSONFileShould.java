package com.zachary_moore.objects;


import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;


public class JSONFileShould {

    @Test
    public void JSONFileGivesJSONArray() throws IOException {
        JSONFile jsonFile = new JSONFile(
                new File(getClass()
                         .getClassLoader()
                         .getResource("array-file.json")
                         .getFile()));
        assert(jsonFile.getObject() instanceof JSONArray);
        assert(((JSONArray)jsonFile.getObject()).length() == 3);
    }

    @Test
    public void JSONFileGivesJSONObject() throws IOException {
        JSONFile jsonFile = new JSONFile(
                new File(getClass()
                        .getClassLoader()
                        .getResource("test-2.json")
                        .getFile()));
        assert(jsonFile.getObject() instanceof JSONObject);
    }
}
