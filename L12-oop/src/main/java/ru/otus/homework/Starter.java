package ru.otus.homework;

import java.util.List;
import ru.otus.homework.config.AppConfig;
import ru.otus.homework.domain.Banknote;
import ru.otus.homework.domain.NominalType;
import ru.otus.homework.service.AtmUsageService;

public class Starter {

    public static void main(String[] args) {
        AtmUsageService atmService = new AtmUsageService(AppConfig.atm(), 50);
        atmService.getMoney(100);
        atmService.getMoney(1000);
        atmService.getMoney(80);
        atmService.putMoney(List.of(new Banknote(NominalType.FIFTY), new Banknote(NominalType.TEN)));
        atmService.getMoney(33);
        atmService.getMoney(0);
    }
}
