package ru.otus.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.dto.ClientData;
import ru.otus.dto.ClientsData;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.service.FrontendService;

@Controller
public class MessageController {

    private final MessageCallback<ClientsData> dataConsumer;
    private final FrontendService frontendService;

    public MessageController(FrontendService frontendService, SimpMessagingTemplate template) {
        this.frontendService = frontendService;
        this.dataConsumer = clients ->
            template.convertAndSend("/topic/clients", clients);
    }

    @MessageMapping("/clients")
    public void getClients() {
        frontendService.getAll(dataConsumer);
    }

    @MessageMapping("/client/save")
    @SendTo("/topic/clients")
    public void saveClient(ClientData clientData) {
        frontendService.saveClient(clientData, dataConsumer);
    }

}
