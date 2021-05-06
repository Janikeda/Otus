package ru.otus.homework.service;

import ru.otus.homework.annotation.Log;

public interface ITestLogging {

    @Log
    void calculation(int param);

    @Log
    void calculation2(int param, int param2);

    @Log
    void calculation3(int param, int param2, int param3);

    @Log
    void calculation4(int param, String param2);

    void calculation5(String param, String param2);
}
