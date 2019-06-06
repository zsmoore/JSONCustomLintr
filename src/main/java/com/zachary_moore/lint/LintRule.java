package com.zachary_moore.lint;

import com.zachary_moore.filters.FilterMapper;
import com.zachary_moore.objects.JSONFile;

import com.zachary_moore.objects.WrappedObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;


/**
 * Class to represent a lint rule configuration
 */
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

    /**
     * Given a list of files lint each file based on setup config
     * @param jsonFiles List of {@link JSONFile} to lint
     * @return {@link Map} representing each file with lint errors mapped to a list of each lint error determined by our {@link LintImplementation}
     * @throws LintImplementation.NoReportSetException if there was no report set during runtime of our {@link LintImplementation}'s report
     */
    @SuppressWarnings("unchecked")
    public Map<JSONFile, List<Error>> lint(JSONFile ...jsonFiles) throws LintImplementation.NoReportSetException{
        Map<JSONFile, List<Error>> reportMessages = new HashMap<>();

        for (JSONFile file: jsonFiles) {

            List<? extends WrappedObject> filteredList = FilterMapper.filter(file, implementation);
            if (filteredList == null) {
                return null;
            }

            for (WrappedObject element : filteredList) {
                if (implementation.shouldReport(element)) {
                    String path = getPath(element);

                    if (!reportMessages.containsKey(file)) {
                        reportMessages.put(file, new ArrayList<>());
                    }
                    reportMessages.get(file).add(new Error(path, implementation.report(element)));
                }
            }
        }

        return reportMessages;
    }

    private String getPath(WrappedObject element) {
        if (StringUtils.isBlank(element.getOriginatingKey()))
            return "";

        if (element.getParentObject() == null || StringUtils.isBlank(element.getParentObject().getOriginatingKey()))
            return element.getOriginatingKey();

        String parentPath = getPath(element.getParentObject());

        if (StringUtils.isBlank(parentPath))
            return element.getOriginatingKey();
        return parentPath + "." + element.getOriginatingKey();
    }

    public LintLevel getLevel() {
        return level;
    }

    public String getIssueId() {
        return issueId;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public String getIssueExplanation() {
        return issueExplanation;
    }

    @Override
    public int hashCode() {
        return this.issueId.hashCode();
    }

    /**
     * Builder pattern class for building our {@link LintRule}
     */
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

        /**
         * Builds a {@link LintRule}
         * @return new {@link LintRule}
         * @throws LintRuleBuilderException if either {@link LintLevel}, IssueID, or {@link LintImplementation} is not set
         */
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

        public static class LintRuleBuilderException extends Exception {
            private LintRuleBuilderException(String errorMessage) {
                super(errorMessage);
            }
        }
    }
}