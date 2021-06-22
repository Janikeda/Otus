package ru.otus.listener;

import ru.otus.model.Message;

public interface DeepCopyService {

    Message deepCopy(Message msg);
}
