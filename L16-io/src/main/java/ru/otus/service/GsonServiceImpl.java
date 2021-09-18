package ru.otus.service;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GsonServiceImpl implements GsonService {


    private static final Set<Class<?>> specialClasses = Set
        .of(Integer.class, Float.class, Double.class, Long.class, Character.class, Boolean.class,
            String.class, Byte.class, Short.class);


    @Override
    public String toJson(Object target) throws NoSuchFieldException {
        List<Field> fields = null;
        if (target.getClass().isArray()) {
            fields = arrayHandler((Object[]) target);
        } else if (specialClasses.contains(target.getClass())) {
            fields = Collections.singletonList(target.getClass().getDeclaredField("value"));
        } else {
            fields = Arrays.stream(target.getClass().getDeclaredFields())
                .collect(Collectors.toList());
        }
        Map<String, Object> fieldNameValueMap = new HashMap<>();
        fields.forEach(field -> {
            field.setAccessible(true);
            try {
                fieldNameValueMap.put(field.getName(), field.get(target));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return null;
    }

    private List<Field> arrayHandler(Object[] target) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < ((Object[]) target).length; i++) {
            list.add(Array.get(target, i));
        }
        return list.stream().map(Object::getClass)
            .flatMap(aClass -> Arrays.stream(aClass.getDeclaredFields()))
            .collect(Collectors.toList());
    }


    @Override
    public Object fromJson(String json, Class<?> clazz) {
        return null;
    }
}
