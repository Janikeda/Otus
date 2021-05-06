package ru.otus.homework.config;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;
import ru.otus.homework.annotation.Log;

public final class AppConfig {

    private AppConfig() {
    }

    public static Object createBean(Object bean) {
        Class<?> beanClass = bean.getClass();
        if (beanClass != null) {
            return Proxy.newProxyInstance(
                beanClass.getClassLoader(),
                beanClass.getInterfaces(),
                (proxy, method, args) -> {
                    Object invoke = method.invoke(bean, args);
                    if (method.isAnnotationPresent(Log.class)) {
                        System.out.printf("executed method: %s, param: %s%n", method.getName(),
                            Arrays.stream(args).map(Object::toString)
                                .collect(Collectors.joining(", ")));
                    }
                    return invoke;
                });
        }
        return bean;
    }
}
