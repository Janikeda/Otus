package ru.otus.appcontainer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        try {
            processConfig(initialConfigClass);
        } catch (Exception e) {
            throw new RuntimeException("ProcessConfig exception", e);
        }
    }

    private void processConfig(Class<?> configClass) throws Exception {
        Object appConfig = configClass.getDeclaredConstructor().newInstance();
        checkConfigClass(configClass);
        List<Method> potentialBeans = Arrays.stream(configClass.getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(AppComponent.class))
            .sorted(
                Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order()))
            .collect(Collectors.toList());

        for (Method method : potentialBeans) {
            String beanName = method.getAnnotation(AppComponent.class).name();
            List<Object> args = getArguments(method);
            Object result = method.invoke(appConfig, args.toArray());

            appComponents.add(result);
            appComponentsByName.put(beanName, result);
        }
    }

    private List<Object> getArguments(Method method) {
        Parameter[] parameters = method.getParameters();
        List<Object> args = new ArrayList<>();
        for (Parameter parameter : parameters) {
            Class<?> type = parameter.getType();
            appComponents.forEach(bean -> {
                if (type.isAssignableFrom(bean.getClass())) {
                    args.add(bean);
                }
            });
        }
        return args;
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(
                String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream()
            .filter((component) ->
                componentClass.isAssignableFrom(component.getClass()))
            .findFirst()
            .orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
