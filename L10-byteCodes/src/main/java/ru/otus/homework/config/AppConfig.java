package ru.otus.homework.config;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import ru.otus.homework.annotation.Log;
import ru.otus.homework.service.ITestLogging;
import ru.otus.homework.service.TestLogging;

public final class AppConfig {

    private AppConfig() {
    }

    public static ITestLogging testLogging() {
        TestLogging testLogging = new TestLogging();
        Class<?> beanClass = testLogging.getClass();
        Map<String, Method> annotatedMethods = findAnnotatedMethods(beanClass);
        return (ITestLogging) Proxy.newProxyInstance(
            beanClass.getClassLoader(),
            beanClass.getInterfaces(),
            (proxy, method, args) -> {
                Object invoke = method.invoke(testLogging, args);
                if (annotatedMethods.get(method.getName()) != null) {
                    System.out.printf("executed method: %s, param: %s%n", method.getName(),
                        Arrays.stream(args).map(Object::toString)
                            .collect(Collectors.joining(", ")));
                }
                return invoke;
            });
    }

    private static Map<String, Method> findAnnotatedMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        Map<String, Method> annotatedMethods = new HashMap<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Log.class)) {
                annotatedMethods.put(method.getName(), method);
            }
        }
        return annotatedMethods;
    }
}
