package ru.otus.listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import ru.otus.exception.DeepCopyException;
import ru.otus.model.Message;
import ru.otus.model.Message.Builder;

public class DeepCopyServiceFirstImpl implements DeepCopyService {

    @Override
    public Message deepCopy(Message msg) {
        Builder builder = msg.toBuilder();
        for (Method method : builder.getClass().getDeclaredMethods()) {
            Optional<Class<?>> classForCopy = Arrays.stream(method.getParameterTypes()).findFirst();
            if (classForCopy.isPresent()) {
                try {
                    Method methodClone;
                    try {
                        methodClone = classForCopy.get().getDeclaredMethod("clone");
                    } catch (NoSuchMethodException e) {
                        continue;
                    }

                    String methodName = "get" + StringUtils.capitalize(method.getName());
                    Method declaredMethod = msg.getClass().getDeclaredMethod(methodName);
                    Object result = declaredMethod.invoke(msg);

                    Object copiedObject = methodClone.invoke(result);

                    method.invoke(builder, copiedObject);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new DeepCopyException(e.getMessage());
                }
            }
        }
        return builder.build();
    }
}
