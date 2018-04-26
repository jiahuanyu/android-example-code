package me.jiahuan.androidlearn.clint;


import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.android.ddmlib.Log;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNewExpression;

import org.jetbrains.uast.UElement;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NewThreadDetector extends Detector implements Detector.UastScanner {
    public static final Issue ISSUE = Issue.create(
            "NewThread",
            "避免自己创建Thread",
            "请勿直接调用new Thread()，建议使用AsyncTask或统一的线程管理工具类",
            Category.PERFORMANCE, 5, Severity.ERROR,
            new Implementation(NewThreadDetector.class, Scope.JAVA_FILE_SCOPE)
    );

    @Override
    public List<String> getApplicableConstructorTypes() {
        return Collections.singletonList("java.lang.Thread");
    }


    @Override
    public void visitConstructor(@NonNull JavaContext context, @Nullable JavaElementVisitor visitor, @NonNull PsiNewExpression node, @NonNull PsiMethod constructor) {
        context.report(ISSUE, node, context.getLocation(node), "请勿直接调用new Thread()，建议使用AsyncTask或统一的线程管理工具类");
    }
}
