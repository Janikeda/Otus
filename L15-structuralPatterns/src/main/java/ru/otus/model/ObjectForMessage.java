package ru.otus.model;

import java.util.List;

public class ObjectForMessage {

    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public ObjectForMessage clone() {
        try {
            return (ObjectForMessage) super.clone();
        } catch (CloneNotSupportedException e) {
            ObjectForMessage objectForMessage = new ObjectForMessage();
            objectForMessage.setData(data);
            return objectForMessage;
        }
    }
}
