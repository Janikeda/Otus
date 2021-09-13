package ru.otus.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("phone")
public class PhoneDataSet {

    @Id
    private Long id;

    @Column("phone_number")
    private String number;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
            "id=" + id +
            ", number='" + number + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhoneDataSet)) {
            return false;
        }
        return id != null && id.equals(((PhoneDataSet) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
