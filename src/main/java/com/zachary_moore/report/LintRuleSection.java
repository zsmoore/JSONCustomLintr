package com.zachary_moore.report;

import com.zachary_moore.lint.LintError;
import j2html.tags.Tag;
import com.zachary_moore.lint.LintLevel;
import com.zachary_moore.lint.LintRule;
import com.zachary_moore.objects.JSONFile;

import java.util.List;
import java.util.Map;

import static j2html.TagCreator.*;

class LintRuleSection {

    private final FileSection fileSection;

    LintRuleSection() {
        this.fileSection = new FileSection();
    }

    Tag getLintRuleSection(Map.Entry<LintRule, Map<JSONFile, List<LintError>>> lintRuleMapEntry) {
        int numTotalIssues = 0;
        for (Map.Entry<JSONFile, List<LintError>> entry : lintRuleMapEntry.getValue().entrySet()) {
            numTotalIssues += entry.getValue().size();
        }
        return div(
                h2(lintRuleMapEntry.getKey().getIssueId() + " (" + lintRuleMapEntry.getKey().getLevel() + ") - " + numTotalIssues + " found" ),
                (lintRuleMapEntry.getKey().getIssueDescription() != null
                        ? h3(lintRuleMapEntry.getKey().getIssueDescription()) : emptyTag("empty")),
                (lintRuleMapEntry.getKey().getIssueExplanation() != null
                        ? h3(lintRuleMapEntry.getKey().getIssueExplanation()) : emptyTag("empty")),
                div(
                    each(lintRuleMapEntry.getValue().entrySet(), pair ->
                            div(
                                this.fileSection.getFileSummary(pair),
                                hr().attr("width", "0%")
                            ).withClass("container-fluid")
                    )
                ).withClasses("container-fluid")
        ).withClasses("container-fluid", lintRuleMapEntry.getKey().getLevel() == LintLevel.ERROR ? "bg-danger" : "bg-warning");
    }

}
