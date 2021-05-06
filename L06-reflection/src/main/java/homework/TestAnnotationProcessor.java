package homework;

import static homework.ReflectionHelper.callMethod;
import static homework.ReflectionHelper.findAnnotatedMethods;
import static homework.ReflectionHelper.instantiate;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestAnnotationProcessor<T> {

    public Result testAnnotationProcessor(Class<T> clazz) {
        List<Method> testMethods = findAnnotatedMethods(clazz, Test.class);

        List<Method> beforeMethods = findAnnotatedMethods(clazz, Before.class).stream()
            .sorted(Comparator.comparingInt(m -> m.getAnnotation(Before.class).order())).collect(
                Collectors.toList());

        List<Method> afterMethods = findAnnotatedMethods(clazz, After.class)
            .stream().sorted(Comparator.comparingInt(m -> m.getAnnotation(After.class).order()))
            .collect(Collectors.toList());

        int success = 0;
        int failures = 0;

        for (Method testMethod : testMethods) {
            T object = instantiate(clazz);

            String testMethodName = testMethod.getName();
            Parameter[] testMethodParameters = testMethod.getParameters();

            success++;
            try {
                this.forEach(object, beforeMethods);
                callMethod(object, testMethodName, (Object[]) testMethodParameters);
                this.forEach(object, afterMethods);
            } catch (Exception exception) {
                failures++;
                success--;
            }
        }
        return new Result(String.format(
            "Статистика тестового класса %s. Всего тестов: %s, прошли успешно: %s, провалено: %s",
            clazz.getSimpleName(),
            testMethods.size(), success, failures));
    }

    private void forEach(T object, List<Method> methods) {
        methods.forEach(method -> {
            String name = method.getName();
            Parameter[] parameters = method.getParameters();
            callMethod(object, name, (Object[]) parameters);
        });
    }

}
