package ru.otus.homework.service;

import ru.otus.homework.annotation.Log;

public class TestLogging implements ITestLogging {

    @Override
    @Log
    public void calculation(int param) {
    }

    @Override
    @Log
    public void calculation2(int param, int param2) {
        System.out.println("test");
    }

    @Override
    @Log
    public void calculation3(int param, int param2, int param3) {
    }

    @Override
    @Log
    public void calculation4(int param, String param2) {
        System.out.println("test");
    }

    @Override
    public void calculation5(String param, String param2) {
    }
}
