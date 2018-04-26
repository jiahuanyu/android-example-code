package me.jiahuan.androidlearn.java_test.设计模式.工厂模式;


public class GDComputerFactor extends ComputerFactory {
    @Override
    public <T extends Computer> T createComputer(Class<T> clz) {
        Computer computer = null;
        String className = clz.getName();

        try {
            computer = (Computer) Class.forName(className).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return (T) computer;
    }
}
