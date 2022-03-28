package application.profiled;

import static application.profiled.RandomJobKt.randomJob;

public class B {
    public void method1(String s) {
        randomJob();
    }

    public void method1(String s, int i) {
        randomJob();
    }

    public void method2(int i, int j) {
        randomJob();
    }

    public void method3(String s) {
        privateMethod();
        protectedStaticMethod();
        randomJob();
    }

    void privateMethod() {
        randomJob(0, 25);
    }

    protected static void protectedStaticMethod() {
        randomJob(0, 25);
    }
}
