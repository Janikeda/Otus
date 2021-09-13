package ru.otus.model;

import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

@Table("client")
public class Client {

    @Id
    private Long id;
    @NonNull
    private String name;

    @MappedCollection(idColumn = "client_id")
    private AddressDataSet addressData;

    @MappedCollection(keyColumn = "client_id", idColumn = "client_id")
    private Set<PhoneDataSet> phoneDataSet = new HashSet<>();

    public Client() {
    }

    public Client(Long id, String name, AddressDataSet addressData,
        Set<PhoneDataSet> phoneDataSet) {
        this.id = id;
        this.name = name;
        this.addressData = addressData;
        this.phoneDataSet = phoneDataSet;
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.addressData, this.phoneDataSet);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressDataSet getAddressData() {
        return addressData;
    }

    public void setAddressData(AddressDataSet addressData) {
        this.addressData = addressData;
    }

    public Set<PhoneDataSet> getPhoneDataSet() {
        return phoneDataSet;
    }

    public void setPhoneDataSet(Set<PhoneDataSet> phoneDataSet) {
        this.phoneDataSet = phoneDataSet;
    }

    public void addPhone(PhoneDataSet phone) {
        this.phoneDataSet.add(phone);
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", AddressDataSet='" + addressData + '\'' +
            ", PhoneDataSet='" + phoneDataSet + '\'' +
            '}';
    }
}
