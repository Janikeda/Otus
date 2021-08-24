package ru.otus.dto;

public class ClientDto {

    public String id;

    public String name;

    public String street;

    public String numbers;

    public ClientDto() {
    }

    public ClientDto(String id, String name, String street, String numbers) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.numbers = numbers;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getNumbers() {
        return numbers;
    }
}
