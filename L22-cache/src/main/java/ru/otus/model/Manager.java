package ru.otus.model;

import ru.otus.reflection.Id;

public class Manager implements Identifiable {

    @Id
    private Long no;

    private String label;

    public Manager() {
    }

    public Manager(String label) {
        this.label = label;
    }

    public Manager(Long no, String label) {
        this.no = no;
        this.label = label;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public Long getId() {
        return this.no;
    }

    @Override
    public String toString() {
        return "Manager{" +
            "no=" + no +
            ", label='" + label + '\'' +
            '}';
    }
}
