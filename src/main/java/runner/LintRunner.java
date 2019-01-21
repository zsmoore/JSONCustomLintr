package runner;

import lint.LintImplementation;
import lint.LintLevel;
import lint.LintRegister;
import lint.LintRule;
import objects.JSONFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LintRunner {

    private List<JSONFile> filesToLint;
    private final LintRegister lintRegister;

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

    public Map<LintRule, Map<JSONFile, List<String>>> lint() {
        Map<LintRule, Map<JSONFile, List<String>>> lintOutput = new HashMap<>();
        for (LintRule lintRule : lintRegister.getLintRules()) {
            try {
                if (lintRule.getLevel() != LintLevel.IGNORE) {
                    lintOutput.put(lintRule, lintRule.lint(filesToLint.toArray(new JSONFile[filesToLint.size()])));
                }
            } catch (LintImplementation.NoReportSetException e) {
                throw new RuntimeException("Lint rule:\t" + lintRule.getIssueId()
                        + " caught lint error but did not have a report string in LintImplementation.");
            }
        }
        return lintOutput;
    }
}
