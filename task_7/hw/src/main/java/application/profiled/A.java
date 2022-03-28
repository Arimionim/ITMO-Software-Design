package application.profiled;

import java.util.Random;

public class A {
    private final Random rand = new Random();

    public void rec1() {
        if (rand.nextInt(10) > 5) {
            rec1();
        }
        if (rand.nextInt(10) > 7) {
            rec2();
        }
        if (rand.nextInt(10) > 8) {
            rec3();
        }
    }

    public void rec2() {
        if (rand.nextInt(10) > 5) {
            rec1();
        }
        if (rand.nextInt(10) > 7) {
            rec2();
        }
        if (rand.nextInt(10) > 8) {
            rec3();
        }
    }

    public void rec3() {
        if (rand.nextInt(10) > 5) {
            rec1();
        }
        if (rand.nextInt(10) > 7) {
            rec2();
        }
        if (rand.nextInt(10) > 8) {
            rec3();
        }
    }
}
