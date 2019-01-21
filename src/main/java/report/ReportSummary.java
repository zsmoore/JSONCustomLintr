package report;

import j2html.tags.Tag;
import lint.LintRule;
import objects.JSONFile;

import java.util.List;
import java.util.Map;

import static j2html.TagCreator.*;

class ReportSummary {

    public Tag getReportSummaryHeader(Map<LintRule, Map<JSONFile, List<String>>> lintOutput) {
        return header(
                    getHeader(),
                    div(
                        h1("Lint Report Summary").withClasses("display-2", "text-white"),
                          table(
                            thead(
                              tr(
                                th("Issue ID:").attr("scope", "col"),
                                th("Num Total Issues:").attr("scope", "col"),
                                th("Num Files With Issues:").attr("scope", "col")
                              )
                            ),
                              tbody(
                                each(lintOutput, pair -> (
                                    summarizeLintRule(pair)
                                )
                              )
                            )
                          ).withClasses("table", "table-dark")
                     ).withClass("container-fluid")
                ).withClasses("bg-info", "container-fluid");
    }

    private Tag summarizeLintRule(Map.Entry<LintRule, Map<JSONFile, List<String>>> inputPair) {
        int numFileIssues = inputPair.getValue().size();
        int numTotalIssues = 0;
        for (Map.Entry<JSONFile, List<String>> fileIssuePair: inputPair.getValue().entrySet()) {
            numTotalIssues += fileIssuePair.getValue().size();
        }
        return tr(
                 th(inputPair.getKey().getIssueId()).attr("scope", "row"),
                 td("" + numTotalIssues),
                 td("" + numFileIssues));
    }

    private Tag getHeader() {
        return div(
                link().withRel("stylesheet")
                        .withHref("https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css")
                        .attr("integrity", "sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS")
                        .attr("crossorigin", "anonymous"),
                meta().attr("charset", "utf-8"),
                meta().attr("name", "viewport")
                      .attr("content", "width=device-width, initial-scale=1, shrink-to-fit=no")
        );
    }
}
