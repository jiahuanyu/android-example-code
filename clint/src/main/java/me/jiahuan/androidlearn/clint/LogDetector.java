package me.jiahuan.androidlearn.clint;


import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UElement;

import java.util.Arrays;
import java.util.List;

public class LogDetector extends Detector implements Detector.UastScanner {

    private static final String ISSUE_DESCRIPTION = "请勿直接调用android.util.Log，应该使用统一工具类";

    public static final Issue ISSUE = Issue.create(
            "LogUsage",
            ISSUE_DESCRIPTION,
            "...",
            Category.SECURITY,
            6,
            Severity.ERROR,
            new Implementation(LogDetector.class, Scope.JAVA_FILE_SCOPE)
    );

    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("v", "d", "i", "w", "e");
    }


    @Override
    public void visitMethod(JavaContext context, UCallExpression node, PsiMethod method) {
        if (context.getEvaluator().isMemberInClass(method, "android.util.Log")) {
            context.report(ISSUE, node, context.getLocation(node), ISSUE_DESCRIPTION);
        }
    }
}
