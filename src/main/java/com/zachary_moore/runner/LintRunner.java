package com.zachary_moore.runner;

import com.zachary_moore.lint.LintImplementation;
import com.zachary_moore.lint.LintLevel;
import com.zachary_moore.lint.LintRegister;
import com.zachary_moore.lint.LintRule;
import com.zachary_moore.objects.JSONFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;


public class LintRunner {

    private String[] basePaths;
    private Map<LintRule, Map<JSONFile, List<String>>> lintOutput;
    private final LintRegister lintRegister;

    /**
     * Create a LintRunner
     * @param lintRegister representation of {@link LintRule} to run against
     * @param basePaths Paths to folders or files to fetch {@link JSONFile}(s) from
     */
    public LintRunner(LintRegister lintRegister,
                      String... basePaths) {
        this.lintRegister = lintRegister;
        this.basePaths = basePaths;
    }

    private Set<JSONFile> getFilesToLint() {
        Set<File> files = new HashSet<>();

        for (String basePath : this.basePaths) {
            File file = new File(basePath);

            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                files.addAll(FileUtils.listFiles(file, null, true));
            } else {
                // TODO: Add proper logging
                Logger.getGlobal().warning(basePath + " is not a valid file path");
            }
        }

        return files.stream().map(file -> {
            try {
                return new JSONFile(file);
            } catch (IOException e) {
                // Is not a JSON file
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * Lint all files in given path in constructor and store the output
     * @return Representation of any lint issues
     */
    public Map<LintRule, Map<JSONFile, List<String>>> lint() {
        Map<LintRule, Map<JSONFile, List<String>>> lintOutput = new HashMap<>();
        Set<JSONFile> filesToLint = getFilesToLint();

        for (LintRule lintRule : lintRegister.getLintRules()) {
            try {
                if (lintRule.getLevel() != LintLevel.IGNORE) {
                    Map<JSONFile, List<String>> lintReports = lintRule.lint(filesToLint.toArray(new JSONFile[filesToLint.size()]));
                    if (lintReports.size() != 0) {
                        lintOutput.put(lintRule, lintReports);
                    }
                }
            } catch (LintImplementation.NoReportSetException e) {
                throw new RuntimeException("Lint rule:\t" + lintRule.getIssueId()
                        + " caught lint error but did not have a report string in LintImplementation.");
            }
        }
        this.lintOutput = lintOutput;

        return lintOutput;
    }

    /**
     * Check lint output results and return proper exit code
     * @return 0 if no {@link LintRule} report errors and are set to {@link LintLevel#ERROR} else 1
     */
    public int analyzeLintAndGiveExitCode() {
        if (this.lintOutput == null) {
            throw new RuntimeException("Attempted to analyze lint results before they were computed.");
        }

        for (Map.Entry<LintRule, Map<JSONFile, List<String>>> entry: this.lintOutput.entrySet()) {
            if (entry.getKey().getLevel() == LintLevel.ERROR
                    && entry.getValue().size() != 0) {
                return 1;
            }
        }

        return 0;
    }
}
