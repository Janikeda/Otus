package ru.otus.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ru.otus.dto.ClientData;
import ru.otus.model.AddressDataSet;
import ru.otus.model.Client;
import ru.otus.model.PhoneDataSet;

@Service
public class ClientMapperService {

    public List<ClientData> fromEntitiesToDtos(List<Client> clients) {
        List<ClientData> result = new ArrayList<>();
        clients.forEach(client -> {
            String phones = client.getPhoneDataSet().stream().map(PhoneDataSet::getNumber).collect(
                Collectors.joining(","));
            ClientData clientData = new ClientData(String.valueOf(client.getId()), client.getName(),
                client.getAddressData().getStreet(), phones);
            result.add(clientData);
        });
        return result;
    }

    public ClientData fromEntityToDto(Client client) {
        String phones = client.getPhoneDataSet().stream().map(PhoneDataSet::getNumber)
            .collect(Collectors.joining(","));
        return new ClientData(String.valueOf(client.getId()), client.getName(),
            client.getAddressData().getStreet(), phones);
    }

    public Client fromDtoToEntity(ClientData clientData) {
        String[] phonesSet = clientData.getNumbers().split(",");
        Set<PhoneDataSet> phoneDataSet = Arrays.stream(phonesSet).map(PhoneDataSet::new)
            .collect(Collectors.toSet());
        Client client = new Client();
        client.setName(clientData.getName());
        client.setAddressData(new AddressDataSet(clientData.getStreet()));
        phoneDataSet.forEach(client::addPhone);
        return client;
    }
}
