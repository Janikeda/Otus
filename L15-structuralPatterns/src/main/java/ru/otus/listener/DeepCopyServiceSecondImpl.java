package ru.otus.listener;

import ru.otus.model.Message;

public class DeepCopyServiceSecondImpl implements DeepCopyService {

    @Override
    public Message deepCopy(Message msg) {
        return msg.toBuilder()
            .field13(msg.getField13().clone())
            .build();
    }
}
