package report;

import j2html.tags.Tag;
import lint.LintRule;
import objects.JSONFile;

import java.util.List;
import java.util.Map;

import static j2html.TagCreator.*;

public class Report {

    private ReportSummary reportSummary;
    private LintRuleSection lintRuleSection;
    private FooterSection footerSection;

    public Report() {
        this.reportSummary = new ReportSummary();
        this.lintRuleSection = new LintRuleSection();
        this.footerSection = new FooterSection();
    }

    public Tag report(Map<LintRule, Map<JSONFile, List<String>>> lintOutput) {
        return html(reportSummary.getReportSummaryHeader(lintOutput),
                    hr(),
                    body(getAllLintRulesSection(lintOutput)),
                    hr(),
                    footerSection.getFooter());
    }

    private Tag getAllLintRulesSection(Map<LintRule, Map<JSONFile, List<String>>> lintOutput) {
        return div(
            each(lintOutput, pair ->
                    this.lintRuleSection.getLintRuleSection(pair)
            ));
    }
}
