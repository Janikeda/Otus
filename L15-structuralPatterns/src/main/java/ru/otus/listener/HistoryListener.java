package ru.otus.listener;

import java.util.Optional;
import ru.otus.model.Message;
import ru.otus.repo.MessageRepository;

public class HistoryListener implements Listener, HistoryReader {

    private final MessageRepository messageRepository;
    private final DeepCopyService deepCopyService;

    public HistoryListener(MessageRepository messageRepository,
        DeepCopyService deepCopyService) {
        this.messageRepository = messageRepository;
        this.deepCopyService = deepCopyService;
    }

    @Override
    public void onUpdated(Message msg) {
        Message copiedMsg = deepCopyService.deepCopy(msg);
        messageRepository.save(copiedMsg);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(messageRepository.getById(id));
    }
}
