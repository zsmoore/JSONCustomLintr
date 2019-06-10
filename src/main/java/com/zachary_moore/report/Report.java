package com.zachary_moore.report;

import com.zachary_moore.lint.LintError;
import j2html.tags.Tag;
import com.zachary_moore.lint.LintRule;
import com.zachary_moore.objects.JSONFile;

import java.util.List;
import java.util.Map;

import static j2html.TagCreator.*;

public class Report {

    private final ReportSummary reportSummary;
    private final LintRuleSection lintRuleSection;
    private final FooterSection footerSection;

    public Report() {
        this.reportSummary = new ReportSummary();
        this.lintRuleSection = new LintRuleSection();
        this.footerSection = new FooterSection();
    }

    /**
     * Generate html report of input lint results
     * @param lintOutput output from a {@link com.zachary_moore.runner.LintRunner} to generate based off of
     * @return HTML representation
     */
    public Tag report(Map<LintRule, Map<JSONFile, List<LintError>>> lintOutput) {
        if (lintOutput.size() == 0) {
            return null;
        }
        return html(div(reportSummary.getReportSummaryHeader(lintOutput),
                    body(getAllLintRulesSection(lintOutput),
                         linkSection()),
                    hr().attr("width", "0%"),
                    footerSection.getFooter()).withClasses("container-fluid", "bg-info")).withClass("bg-info");
    }

    private Tag getAllLintRulesSection(Map<LintRule, Map<JSONFile, List<LintError>>> lintOutput) {
        return div(
            hr().attr("width", "0%"),
            each(lintOutput, this.lintRuleSection::getLintRuleSection
            )).withClasses("container-fluid", "bg-info", "border");
    }

    private Tag linkSection() {
        return div(
                script().withSrc("https://code.jquery.com/jquery-3.3.1.slim.min.js")
                        .attr("integrity", "sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo")
                        .attr("crossorigin", "anonymous"),
                script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js")
                        .attr("integrity","sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut")
                        .attr("crossorigin", "anonymous"),
                script().withSrc("https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js")
                        .attr("integrity", "sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k")
                        .attr("crossorigin", "anonymous")
        );
    }
}
