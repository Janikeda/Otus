package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.handlers.GetAllClientsRequestHandler;
import ru.otus.handlers.GetAllClientsResponseHandler;
import ru.otus.handlers.SaveClientRequestHandler;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.CallbackRegistryImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.service.ClientService;
import ru.otus.service.FrontendService;
import ru.otus.service.FrontendServiceImpl;

@Configuration
public class MessageSystemConfig {

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";


    @Bean
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public CallbackRegistry callbackRegistry() {
        return new CallbackRegistryImpl();
    }

    @Bean
    public MsClient databaseMsClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry,
        ClientService clientService) {
        var handlersStore = new HandlersStoreImpl();
        handlersStore
            .addHandler(MessageType.GET_ALL, new GetAllClientsRequestHandler(clientService));
        handlersStore
            .addHandler(MessageType.SAVE, new SaveClientRequestHandler(clientService));
        var databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem,
            handlersStore, callbackRegistry);
        messageSystem.addClient(databaseMsClient);
        return databaseMsClient;
    }

    @Bean
    public MsClient frontendMsClient(MessageSystem messageSystem,
        CallbackRegistry callbackRegistry) {
        var handlersStore = new HandlersStoreImpl();
        var handler = new GetAllClientsResponseHandler(callbackRegistry);
        handlersStore.addHandler(MessageType.GET_ALL, handler);
        handlersStore.addHandler(MessageType.SAVE, handler);
        var frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem,
            handlersStore, callbackRegistry);
        messageSystem.addClient(frontendMsClient);
        return frontendMsClient;
    }

    @Bean
    public FrontendService frontendService(MsClient frontendMsClient) {
        return new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
    }
}
