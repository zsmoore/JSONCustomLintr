package report;

import j2html.tags.Tag;
import lint.LintLevel;
import lint.LintRule;
import objects.JSONFile;

import java.util.List;
import java.util.Map;

import static j2html.TagCreator.*;

class LintRuleSection {

    private FileSection fileSection;

    LintRuleSection() {
        this.fileSection = new FileSection();
    }

    Tag getLintRuleSection(Map.Entry<LintRule, Map<JSONFile, List<String>>> lintRuleMapEntry) {
        int numTotalIssues = 0;
        for (Map.Entry<JSONFile, List<String>> entry : lintRuleMapEntry.getValue().entrySet()) {
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
