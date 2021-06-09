package ru.otus.homework.service;

import java.util.List;
import java.util.Map;
import ru.otus.homework.domain.Banknote;
import ru.otus.homework.domain.NominalType;
import ru.otus.homework.exception.AtmException;

public class AtmValidationServiceImpl implements AtmValidationService {

    private static volatile AtmValidationServiceImpl validationService;

    private final CalculationService calculationService;

    private AtmValidationServiceImpl(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    public static AtmValidationServiceImpl createAtmValidationService(
        CalculationService calculationService) {
        AtmValidationServiceImpl result = validationService;
        if (result != null) {
            return result;
        }
        synchronized (AtmValidationServiceImpl.class) {
            if (validationService == null) {
                validationService = new AtmValidationServiceImpl(calculationService);
            }
            return validationService;
        }
    }

    @Override
    public void checkAmount(long amount, Map<NominalType, List<Banknote>> bankAccount) {
        if (amount == 0) {
            throw new AtmException("Сумма к выдаче должна быть больше 0");
        }

        long bankAccountAmount = calculationService.calculateBankAccount(bankAccount);
        if (bankAccountAmount < amount) {
            throw new AtmException(
                "\nДля вашего запроса " + amount + " не достаточно средств.\nНа счету: "
                    + bankAccountAmount + "");
        }

        if (amount % 5 != 0) {
            throw new AtmException("Сумма к выдаче должна быть кратна 5");
        }
    }
}
