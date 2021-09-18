package ru.otus;

import ru.otus.model.Dto;
import ru.otus.service.GsonService;
import ru.otus.service.GsonServiceImpl;

public class Demo {

    public static void main(String[] args) throws NoSuchFieldException {
        GsonService gsonService = new GsonServiceImpl();
        //String json = gsonService.toJson(new Dto("Max", 22));
        //gsonService.toJson(new Dto[]{new Dto("Max", 22), new Dto("Vax", 43)});
        gsonService.toJson(new Dto("Max", 22));
    }
}
