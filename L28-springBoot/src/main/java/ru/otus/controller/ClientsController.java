package ru.otus.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.otus.dto.ClientDto;
import ru.otus.service.ClientService;

@Controller
public class ClientsController {

    private final ClientService clientService;

    public ClientsController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public String getClients(Model model) {
        List<ClientDto> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addClient(ClientDto client, RedirectAttributes redirectAttributes) {
        if (client.getName() == null || client.getName().isBlank()) {
            String error = "Ошибка при создании клиента. Нужно указать имя!";
            redirectAttributes.addFlashAttribute("errorMessage", error);
        } else {
            clientService.saveClient(client);
        }
        return "redirect:/clients";
    }
}
