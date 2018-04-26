package me.jiahuan.androidlearn.java_test.设计模式.代理模式;

/**
 * Created by vendor on 2018/4/21.
 */

public class Purchasing implements IShop {
    IShop iShop;

    public Purchasing(IShop iShop) {
        this.iShop = iShop;
    }

    @Override
    public void buy() {
        iShop.buy();
    }
}
