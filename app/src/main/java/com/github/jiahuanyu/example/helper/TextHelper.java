package com.github.jiahuanyu.example.helper;

import android.widget.EditText;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by doom on 15/6/25.
 */
public class TextHelper {
    public static boolean isEmpty(String... str) {
        for (String s : str) {
            if (s == null || "".equals(s.trim()) || "null".equals(s.trim())) {
                return true;
            }
        }
        return false;
    }

    public static String trimOther(String str) {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(str);
        str = m.replaceAll("");
        return str;
    }

    public static boolean isEmpty(EditText et) {
        return isEmpty(et.getText().toString());
    }

    public static boolean isMobileNumber(String mobile) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public static boolean isPasswordValid(String pwd) {
        if (pwd == null) {
            return false;
        }
        if (pwd.length() < 6 || pwd.length() > 16) {
            return false;
        }
        return true;
    }

    public static boolean isPasswordValid(EditText et) {
        return isPasswordValid(et.getText().toString());
    }

    public static boolean isEqual(EditText et1, EditText et2) {
        if (isEmpty(et1) || isEmpty(et2)) {
            return false;
        }
        if (!et1.getText().toString().equals(et2.getText().toString())) {
            return false;
        }
        return true;
    }

    /**
     * 前面拼接字符串
     *
     * @param str
     * @param preStr
     * @param count
     */
    public static String preConcat(String str, String preStr, int count) {
        if (!isEmpty(str, preStr)) {
            while (count-- > 0) {
                str = preStr + str;
            }
        }
        return str;
    }

    /**
     * 后面拼接字符串
     *
     * @param str
     * @param postStr
     * @param count
     */
    public static String postConcat(String str, String postStr, int count) {
        if (!isEmpty(str, postStr)) {
            while (count-- > 0) {
                str += postStr;
            }
        }
        return str;
    }


    /**
     * 是否是中文
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 是否包含中文
     *
     * @param value
     * @return
     */
    public static boolean containsChinese(String value) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(value);
        if (m.find()) {
            return true;
        }
        return false;
    }


    public static String noScienceExpress(double num) {
        BigDecimal bigDecimal = new BigDecimal(num + "");
        return bigDecimal.toString();
    }

}
