package learn;

import objects.JSONFile;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.logging.Logger;

public class MainTest {

    private JSONFile jsonFile;
    private static Logger log;


    @Before
    public void setup() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test-file.pdsc").getFile());
        jsonFile = new JSONFile(file);
        log = Logger.getLogger("Logger");
    }

    @Test
    public void testJson() throws Exception {
        jsonFile.getObject().toMap().entrySet().forEach(entry ->
            log.info(entry.getKey() + "\t" + entry.getValue()));
    }
}
