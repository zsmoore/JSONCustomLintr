package report;

import j2html.tags.Tag;
import lint.LintRule;
import objects.JSONFile;

import java.util.List;
import java.util.Map;

import static j2html.TagCreator.*;

public class ReportSummary {

    public Tag getReportSummaryHeader(Map<LintRule, Map<JSONFile, List<String>>> lintOutput) {
        return header(
                h1("Lint Report Summary"),
                div(
                  table(
                    thead(
                      tr(
                        th("Issue ID"),
                        th("Num Total Issues"),
                        th("Num Files With Issues")
                      )
                    ),
                      tbody(
                        each(lintOutput, pair -> (
                            summarizeLintRule(pair)
                        )
                      )
                    )
                  )
                )
        );
    }

    private Tag summarizeLintRule(Map.Entry<LintRule, Map<JSONFile, List<String>>> inputPair) {
        int numFileIssues = inputPair.getValue().size();
        int numTotalIssues = 0;
        for (Map.Entry<JSONFile, List<String>> fileIssuePair: inputPair.getValue().entrySet()) {
            numTotalIssues += fileIssuePair.getValue().size();
        }
        return tr(
                 td(inputPair.getKey().getIssueId()),
                 td("" + numTotalIssues),
                 td("" + numFileIssues));
    }
}
