package ru.otus.model;

import java.util.List;

public class Info implements Cloneable {

    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public Info clone() {
        try {
            Info clone = (Info) super.clone();
            clone.setData(data);
            return clone;
        } catch (CloneNotSupportedException e) {
            Info Info = new Info();
            Info.setData(data);
            return Info;
        }
    }
}
