package report;

import j2html.tags.Tag;
import lint.LintRule;
import objects.JSONFile;

import java.util.List;
import java.util.Map;

public class Report {

    private ReportSummary reportSummary;
    private LintRuleSection lintRuleSection;
    private FileSection fileSection;

    public Report() {
        this.reportSummary = new ReportSummary();
        this.lintRuleSection = new LintRuleSection();
        this.fileSection = new FileSection();

    }

    public Tag report(Map<LintRule, Map<JSONFile, List<String>>> lintOutput) {
        return reportSummary.getReportSummaryHeader(lintOutput);
    }



}
