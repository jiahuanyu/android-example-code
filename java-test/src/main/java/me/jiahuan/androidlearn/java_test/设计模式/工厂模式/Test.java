package me.jiahuan.androidlearn.java_test.设计模式.工厂模式;


public class Test {
    public static void main(String[] args) {
        ComputerFactory computerFactory = new GDComputerFactor();
        LenovoComputer lenovoComputer = computerFactory.createComputer(LenovoComputer.class);
    }
}
