package ru.otus.homework.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import ru.otus.homework.domain.Banknote;
import ru.otus.homework.domain.NominalType;
import ru.otus.homework.dto.BalanceAmount;

public final class Atm {

    private static volatile Atm atm;

    private final Map<NominalType, List<Banknote>> bankAccount;
    private final CalculationService calculationService;
    private final AtmValidationService validationService;

    private Atm(Map<NominalType, List<Banknote>> bankAccount,
        CalculationService calculationService, AtmValidationService validationService) {
        this.bankAccount = bankAccount;
        Arrays.stream(NominalType.values()).forEach(banknoteType -> {
            bankAccount.put(banknoteType, new ArrayList<>());
        });
        this.calculationService = calculationService;
        this.validationService = validationService;
    }

    public static Atm createAtm(Map<NominalType, List<Banknote>> bankAccount,
        CalculationService calculationService, AtmValidationService validationService) {
        Atm result = atm;
        if (result != null) {
            return result;
        }
        synchronized (Atm.class) {
            if (atm == null) {
                atm = new Atm(bankAccount, calculationService, validationService);
            }
            return atm;
        }
    }

    public void putMoney(List<Banknote> money) {
        for (Banknote banknote : money) {
            List<Banknote> banknotes = bankAccount.get(banknote.getNominalType());
            banknotes.add(banknote);
        }
    }

    public List<Banknote> getMoney(long amount) {
        validationService.checkAmount(amount, bankAccount);

        return calculationService.getMoney(amount, bankAccount);
    }

    public BalanceAmount checkBalanceAmount() {
        return new BalanceAmount(calculationService.calculateBankAccount(bankAccount));
    }
}
