package report;

import j2html.tags.Tag;
import objects.JSONFile;

import java.util.List;
import java.util.Map;

import static j2html.TagCreator.*;

class FileSection {

    Tag getFileSummary(Map.Entry<JSONFile, List<String>> jsonFileToReports) {
        return div(
                h4(jsonFileToReports.getKey().getFilePath()).withClass("text-white"),
                div(
                  each(jsonFileToReports.getValue(), e -> p(e).withClass("text-white"))
               ).withClass("container-fluid")
        ).withClasses("container-fluid", "border", "bg-dark");
    }
}
