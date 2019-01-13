package lint;

import objects.JSONFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LintRegister {

    private List<LintRule> lintRules;
    private List<JSONFile> filesToLint;

    public LintRegister() {
        lintRules = new ArrayList<>();
        filesToLint = new ArrayList<>();
    }

    public void addFile(JSONFile f) throws  Exception{
        filesToLint.add(f);
    }

    public void register(LintRule ...toRegister) {
        lintRules.addAll(Arrays.asList(toRegister));
    }

    public void lint() {
        for (LintRule lintRule : lintRules) {
            for (JSONFile jsonFile: filesToLint) {
                lintRule.lint(jsonFile);
            }
        }
    }
}
