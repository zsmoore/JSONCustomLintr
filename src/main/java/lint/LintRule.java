package lint;

import filters.FilterMapper;
import objects.JSONFile;
import objects.JSONObject;

import java.util.List;

public class LintRule {

    private final LintLevel level;
    private final LintImplementation implementation;
    private final String issueId;
    private final String issueDescription;
    private final String issueExplanation;


    private LintRule(LintLevel level,
                     LintImplementation implementation,
                     String issueId,
                     String issueDescription,
                     String issueExplanation) {
        this.level = level;
        this.implementation = implementation;
        this.issueId = issueId;
        this.issueDescription = issueDescription;
        this.issueExplanation = issueExplanation;
    }

    @SuppressWarnings("unchecked")
    public void lint(JSONFile jsonFile) {
        List<?> filteredList = FilterMapper.filter(jsonFile, implementation);
        if (filteredList == null) {
            return;
        }

        for (Object element : filteredList) {
            if (implementation.shouldReport(element)) {
                System.out.println("Error in " + jsonFile.getFilePath());
            }
        }
    }


    public static class Builder {

        private LintLevel level;
        private LintImplementation implementation;
        private String issueId;
        private String issueDescription;
        private String issueExplanation;

        public Builder setLevel(LintLevel level) {
            this.level = level;
            return this;
        }

        public Builder setImplementation(LintImplementation implementation) {
            this.implementation = implementation;
            return this;
        }

        public Builder setIssueId(String issueId) {
            this.issueId = issueId;
            return this;
        }

        public Builder setIssueDescription(String issueDescription) {
            this.issueDescription = issueDescription;
            return this;
        }

        public Builder setIssueExplanation(String issueExplanation) {
            this.issueExplanation = issueExplanation;
            return this;
        }

        public LintRule build() throws LintRuleBuilderException {
            if (level == null
                    || implementation == null
                    || issueId == null) {
                throw  new LintRuleBuilderException("Must set LintLevel, LintImplementation and IssueId in a lint rule");
            }

            return new LintRule(level,
                                implementation,
                                issueId,
                                issueDescription,
                                issueExplanation);
        }

        private static class LintRuleBuilderException extends Exception {
            private LintRuleBuilderException(String errorMessage) {
                super(errorMessage);
            }
        }
    }


}
