package me.jiahuan.androidlearn.clint;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CIssueRegistry extends IssueRegistry {
    @Override
    public List<Issue> getIssues() {
        return Arrays.asList(LogDetector.ISSUE, NewThreadDetector.ISSUE);
    }
}
