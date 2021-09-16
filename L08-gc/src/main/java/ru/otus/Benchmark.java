package ru.otus;

import java.util.LinkedList;
import java.util.List;

public class Benchmark implements BenchmarkMBean {

    private final int loopCounter;

    public Benchmark(int loopCounter) {
        this.loopCounter = loopCounter;
    }

    private final List<Object[]> list = new LinkedList<>();


    void run() throws InterruptedException {
        int size = 1;
        for (int i = 0; i < loopCounter; i++) {
            for (int idx = 0; idx < loopCounter; idx++) {
                list.add(new Object[size]);
            }
            size++;
            list.subList(0, list.size() / 2).clear();
            System.out.println("Size is: " + size);
            Thread.sleep(750);
        }
    }
}
