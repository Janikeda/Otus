package ru.otus.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.otus.dto.ClientDto;
import ru.otus.service.ClientService;

@Controller
public class ClientsController {

    private final ClientService clientService;

    public ClientsController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public String init(Model model) {
        List<ClientDto> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public String addPersonSave(Model model, ClientDto client) {
        boolean result = clientService.saveClient(client);
        if (!result) {
            String error = "Ошибка при создании клиента: " + client.getName();
            model.addAttribute("errorMessage", error);
        }
        return "redirect:/clients";
    }
}
