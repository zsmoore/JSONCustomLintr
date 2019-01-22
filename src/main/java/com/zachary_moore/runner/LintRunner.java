package com.zachary_moore.runner;

import com.zachary_moore.lint.LintImplementation;
import com.zachary_moore.lint.LintLevel;
import com.zachary_moore.lint.LintRegister;
import com.zachary_moore.lint.LintRule;
import com.zachary_moore.objects.JSONFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LintRunner {

    private List<JSONFile> filesToLint;
    private Map<LintRule, Map<JSONFile, List<String>>> lintOutput;
    private final LintRegister lintRegister;

    /**
     * Create a LintRunner
     * @param lintRegister representation of {@link LintRule} to run against
     * @param basePath Base Path to setup {@link JSONFile} from
     */
    public LintRunner(LintRegister lintRegister,
                      String basePath) {
        this.lintRegister = lintRegister;

        File basePathFile = new File(basePath);
        if (basePathFile.isFile()) {
            try {
                this.filesToLint = new ArrayList<>(Collections.
                        singletonList(new JSONFile(basePathFile)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (basePathFile.isDirectory()){
            this.filesToLint = getAllFiles(basePath).stream()
                    .map(f -> {
                        try {
                            return new JSONFile(f);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Input path is neither a file nor directory");
        }
    }

    private List<File> getAllFiles(String basePath) {
        List<File> files = new ArrayList<>();
        File directory = new File(basePath);

        File[] fList = directory.listFiles();
        if (fList != null) {
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    files.addAll(getAllFiles(file.getAbsolutePath()));
                }
            }
        }
        return files;
    }

    /**
     * Lint all files in given path in constructor
     * @return Representation of any lint issues
     */
    public Map<LintRule, Map<JSONFile, List<String>>> lint() {
        Map<LintRule, Map<JSONFile, List<String>>> lintOutput = new HashMap<>();
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
