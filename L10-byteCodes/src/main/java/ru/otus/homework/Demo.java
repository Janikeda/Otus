package ru.otus.homework;

import ru.otus.homework.config.AppConfig;
import ru.otus.homework.service.ITestLogging;
import ru.otus.homework.service.TestLogging;

public class Demo {

    public static void main(String[] args) {
        ITestLogging testLogging = (ITestLogging) AppConfig.createBean(new TestLogging());
        testLogging.calculation(7);
        testLogging.calculation2(7, 9);
        testLogging.calculation3(7, 4, 1);
        testLogging.calculation4(7, "Hello");
        testLogging.calculation5("Hello", "World");
    }
}
