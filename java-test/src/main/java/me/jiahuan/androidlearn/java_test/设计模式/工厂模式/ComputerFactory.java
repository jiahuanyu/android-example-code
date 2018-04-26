package me.jiahuan.androidlearn.java_test.设计模式.工厂模式;


public abstract class ComputerFactory {
    public abstract <T extends Computer> T createComputer(Class<T> clz);
}
