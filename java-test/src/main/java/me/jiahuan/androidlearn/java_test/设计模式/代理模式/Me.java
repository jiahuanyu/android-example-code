package me.jiahuan.androidlearn.java_test.设计模式.代理模式;

/**
 * Created by vendor on 2018/4/21.
 */

public class Me implements IShop {
    @Override
    public void buy() {
        System.out.println("Me 购买");
    }
}
