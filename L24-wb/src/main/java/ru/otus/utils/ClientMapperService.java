package ru.otus.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ru.otus.dto.ClientDto;
import ru.otus.model.Client;
import ru.otus.model.PhoneDataSet;

public final class ClientMapperService {

    private ClientMapperService() {
    }

    public static List<ClientDto> fromEntitiesToDtos(List<Client> clients) {
        List<ClientDto> result = new ArrayList<>();
        clients.forEach(client -> {
            String phones = client.getPhoneDataSet().stream().map(PhoneDataSet::getNumber).collect(
                Collectors.joining(","));
            ClientDto clientDto = new ClientDto(String.valueOf(client.getId()), client.getName(),
                client.getAddressData().getStreet(), phones);
            result.add(clientDto);
        });
        return result;
    }

    public static ClientDto fromEntityToDto(Client client) {
        String phones = client.getPhoneDataSet().stream().map(PhoneDataSet::getNumber)
            .collect(Collectors.joining(","));
        return new ClientDto(String.valueOf(client.getId()), client.getName(),
            client.getAddressData().getStreet(), phones);
    }
}
