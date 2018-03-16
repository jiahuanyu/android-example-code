package me.jiahuan.androidlearn;

/**
 * Created by vendor on 2018/3/6.
 */

public class TestJ {
    private static int x = 2;
    private int iI = 1;

    protected static void call() {

    }

    class TestJInner {
        //private static String a = "xxx";

        void s() {
            x++;
            iI++;
        }
    }

    static class TestJInner2 {
        private static String b = "xxxx";

        void y() {
            x++;
           //iI++;
        }
    }
}
