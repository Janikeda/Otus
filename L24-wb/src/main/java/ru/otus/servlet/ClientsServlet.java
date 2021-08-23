package ru.otus.servlet;

import static ru.otus.utils.ClientMapperService.fromEntitiesToDtos;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import ru.otus.dao.ClientDao;
import ru.otus.dto.ClientDto;
import ru.otus.model.AddressDataSet;
import ru.otus.model.Client;
import ru.otus.model.PhoneDataSet;
import ru.otus.service.TemplateProcessor;

public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_CLIENTS = "data";

    private static final String CLIENT_NAME_INPUT = "clientNameInput";
    private static final String ADDRESS_INPUT = "addressInput";
    private static final String PHONES = "phones";

    private final ClientDao clientDao;
    private final TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, ClientDao clientDao) {
        this.templateProcessor = templateProcessor;
        this.clientDao = clientDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = prepareParams();

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        clientDao.save(prepareClient(request));

        response.sendRedirect("/clients");
    }

    private Map<String, Object> prepareParams() {
        Map<String, Object> paramsMap = new HashMap<>();
        List<ClientDto> clients = fromEntitiesToDtos(clientDao.findAll());
        paramsMap.put(TEMPLATE_ATTR_CLIENTS, clients);
        return paramsMap;
    }

    private Client prepareClient(HttpServletRequest request) {
        String clientName = request.getParameter(CLIENT_NAME_INPUT);
        String address = request.getParameter(ADDRESS_INPUT);
        String phones = request.getParameter(PHONES);
        String[] phonesSet = phones.split(",");
        Set<PhoneDataSet> phoneDataSet = Arrays.stream(phonesSet).map(PhoneDataSet::new)
            .collect(Collectors.toSet());
        Client client = new Client();
        client.setName(clientName);
        client.setAddressData(new AddressDataSet(address));
        phoneDataSet.forEach(client::addPhone);
        return client;
    }

}
