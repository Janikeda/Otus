package ru.otus.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ru.otus.dto.ClientDto;
import ru.otus.model.AddressDataSet;
import ru.otus.model.Client;
import ru.otus.model.PhoneDataSet;

@Service
public class ClientMapperService {

    public List<ClientDto> fromEntitiesToDtos(List<Client> clients) {
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

    public ClientDto fromEntityToDto(Client client) {
        String phones = client.getPhoneDataSet().stream().map(PhoneDataSet::getNumber)
            .collect(Collectors.joining(","));
        return new ClientDto(String.valueOf(client.getId()), client.getName(),
            client.getAddressData().getStreet(), phones);
    }

    public Client fromDtoToEntity(ClientDto clientDto) {
        String[] phonesSet = clientDto.getNumbers().split(",");
        Set<PhoneDataSet> phoneDataSet = Arrays.stream(phonesSet).map(PhoneDataSet::new)
            .collect(Collectors.toSet());
        Client client = new Client();
        client.setName(clientDto.getName());
        client.setAddressData(new AddressDataSet(clientDto.getStreet()));
        phoneDataSet.forEach(client::addPhone);
        return client;
    }
}
