package report;

import j2html.TagCreator;
import j2html.tags.Tag;
import objects.JSONFile;

import java.util.List;
import java.util.Map;

import static j2html.TagCreator.*;

class FileSection {

    Tag getFileSummary(Map.Entry<JSONFile, List<String>> jsonFileToReports) {
        return div(
                h4(jsonFileToReports.getKey().getFilePath()),
                div(
                  each(jsonFileToReports.getValue(), TagCreator::p)
               ));
    }
}
