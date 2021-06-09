package ru.otus.listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import ru.otus.exception.DeepCopyException;
import ru.otus.model.Message;
import ru.otus.model.Message.Builder;
import ru.otus.repo.MessageRepository;

public class HistoryListener implements Listener, HistoryReader {

    private final MessageRepository messageRepository;

    public HistoryListener(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void onUpdated(Message msg) {
        Message copiedMsg = deepCopy(msg);
        messageRepository.save(copiedMsg);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(messageRepository.getById(id));
    }

    private static Message deepCopy(Message msg) {
        Builder builder = msg.toBuilder();
        List<Method> fieldsToCopy = findAllFieldsToCopy(builder.getClass());
        for (Method method : fieldsToCopy) {
            Class<?> classForCopy = method.getParameterTypes()[0];
            try {
                Method methodClone = classForCopy.getDeclaredMethod("clone");
                String methodName = "get" + StringUtils.capitalize(method.getName());

                Method declaredMethod = msg.getClass().getDeclaredMethod(methodName);
                Object result = declaredMethod.invoke(msg);

                if (result == null) {
                    continue;
                }
                Object copiedObject = methodClone.invoke(result);

                method.invoke(builder, copiedObject);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new DeepCopyException(e.getMessage());
            }
        }
        return builder.build();
    }

    private static List<Method> findAllFieldsToCopy(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        return Arrays.stream(methods)
            .filter(method -> Arrays.stream(method.getParameterTypes()).count() == 1)
            .filter(method -> Arrays.stream(method.getParameterTypes())
                .allMatch(Cloneable.class::isAssignableFrom)).collect(Collectors.toList());
    }

}
