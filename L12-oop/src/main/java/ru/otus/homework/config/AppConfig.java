package ru.otus.homework.config;

import java.util.HashMap;
import ru.otus.homework.service.Atm;
import ru.otus.homework.service.AtmValidationService;
import ru.otus.homework.service.AtmValidationServiceImpl;
import ru.otus.homework.service.CalculationService;
import ru.otus.homework.service.CalculationServiceImpl;

public class AppConfig {

    public static Atm atm() {
        return Atm.createAtm(new HashMap<>(), calculationService(), validationService());
    }

    private static CalculationService calculationService() {
        return CalculationServiceImpl.createCalculationService();
    }

    private static AtmValidationService validationService() {
        return AtmValidationServiceImpl.createAtmValidationService(calculationService());
    }
}
