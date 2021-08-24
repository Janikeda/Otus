package ru.otus.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private AddressDataSet addressData;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "client_id")
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
