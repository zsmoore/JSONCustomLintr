package com.zachary_moore.lint;

import com.zachary_moore.filters.FilterMapper;
import com.zachary_moore.objects.JSONFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


/**
 * Class to represent a lint rule configuration
 */
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LintRule {

    @Getter
    @NonNull
    private final LintLevel level;

    @NonNull
    private final LintImplementation implementation;

    @Getter
    @NonNull
    @EqualsAndHashCode.Include
    private final String issueId;

    @Getter
    private final String issueDescription;

    @Getter
    private final String issueExplanation;

    /**
     * Given a list of files lint each file based on setup config
     * @param jsonFiles List of {@link JSONFile} to lint
     * @return {@link Map} representing each file with lint errors mapped to a list of each lint error determined by our {@link LintImplementation}
     * @throws LintImplementation.NoReportSetException if there was no report set during runtime of our {@link LintImplementation}'s report
     */
    @SuppressWarnings("unchecked")
    public Map<JSONFile, List<String>> lint(JSONFile ...jsonFiles) throws LintImplementation.NoReportSetException{
        Map<JSONFile, List<String>> reportMessages = new HashMap<>();
        for (JSONFile file: jsonFiles) {

            List<?> filteredList = FilterMapper.filter(file, implementation);
            if (filteredList == null) {
                return null;
            }

            for (Object element : filteredList) {
                if (implementation.shouldReport(element)) {
                    if (!reportMessages.containsKey(file)) {
                        reportMessages.put(file, new ArrayList<>());
                    }
                    reportMessages.get(file).add(implementation.report(element));
                }
            }
        }

        return reportMessages;
    }
}