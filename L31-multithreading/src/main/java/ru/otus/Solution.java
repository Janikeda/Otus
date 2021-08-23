package ru.otus;

import static java.lang.Thread.sleep;

import java.util.concurrent.atomic.AtomicBoolean;

public class Solution {

    private static final int MIN = 1;
    private static final int MAX = 10;
    private int counter1 = 0;
    private int counter2 = 0;
    private final AtomicBoolean flag = new AtomicBoolean(false);


    public static void main(String[] args) {
        Solution solution = new Solution();
        Thread thread1 = new Thread(solution::task1);
        thread1.setName("Thread1");
        Thread thread2 = new Thread(solution::task2);
        thread2.setName("Thread2");
        thread1.start();
        thread2.start();
    }

    private void task1() {
        int step = 1;
        for (int i = 0; i < 30; i++) {
            try {
                synchronized (this) {
                    while (flag.get()) {
                        wait();
                    }
                    counter1 = counter1 + step;

                    printInfo(counter1);
                    step = defineStep(counter1, step);

                    flag.set(true);
                    notifyAll();
                    sleep(1_000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void task2() {
        int step = 1;
        for (int i = 0; i < 30; i++) {
            try {
                synchronized (this) {
                    while (!flag.get()) {
                        wait();
                    }
                    counter2 = counter2 + step;

                    printInfo(counter2);
                    step = defineStep(counter2, step);

                    flag.set(false);
                    notifyAll();
                    sleep(1_000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private int defineStep(int counter, int step) {
        int result = step;
        if (counter == MAX) {
            result = -1;
        }
        if (counter == MIN) {
            result = 1;
        }
        return result;
    }

    private void printInfo(int counter) {
        System.out
            .printf("Thread name: %s, counter: %s%n", Thread.currentThread().getName(),
                counter);
    }

}
