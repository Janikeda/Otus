package ru.otus.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("address")
public class AddressDataSet {

    @Id
    private Long id;

    private String street;


    public AddressDataSet() {
    }

    public AddressDataSet(String street) {
        this.street = street;
    }

    public AddressDataSet(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
            "id=" + id +
            ", street='" + street + '\'' +
            '}';
    }
}
