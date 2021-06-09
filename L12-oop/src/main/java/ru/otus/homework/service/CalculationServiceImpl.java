package ru.otus.homework.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ru.otus.homework.domain.Banknote;
import ru.otus.homework.domain.NominalType;

public class CalculationServiceImpl implements CalculationService {

    private static volatile CalculationServiceImpl calculationService;

    private CalculationServiceImpl() {
    }

    public static CalculationServiceImpl createCalculationService() {
        CalculationServiceImpl result = calculationService;
        if (result != null) {
            return result;
        }
        synchronized (CalculationServiceImpl.class) {
            if (calculationService == null) {
                calculationService = new CalculationServiceImpl();
            }
            return calculationService;
        }
    }

    @Override
    public long calculateBankAccount(Map<NominalType, List<Banknote>> bankAccount) {
        return bankAccount.values().stream()
            .flatMap(List::stream)
            .map(Banknote::getNominalType)
            .map(NominalType::getNominal)
            .reduce(0, Integer::sum);
    }

    @Override
    public List<Banknote> getMoney(long amount, Map<NominalType, List<Banknote>> bankAccount) {
        List<Banknote> sumForReturn = new ArrayList<>();

        NominalType.getSortedNominals().forEach(nominalType -> {
            if (amount >= nominalType.getNominal()) {
                List<Banknote> banknotes = bankAccount.get(nominalType);
                long sum;
                for (Banknote banknote : banknotes) {
                    sum = calculateBanknotes(sumForReturn);
                    if (sum == amount) {
                        break;
                    }
                    if (sum + banknote.getNominalType().getNominal() <= amount) {
                        sumForReturn.add(banknote);
                    }
                }
            }
        });
        deleteBanknotesFromAccount(sumForReturn, bankAccount);
        return sumForReturn;
    }

    private void deleteBanknotesFromAccount(List<Banknote> banknotes,
        Map<NominalType, List<Banknote>> bankAccount) {
        for (Banknote banknote : banknotes) {
            List<Banknote> banknotesFromAccount = bankAccount.get(banknote.getNominalType());
            banknotesFromAccount.remove(banknote);
        }
    }

    private long calculateBanknotes(List<Banknote> banknotes) {
        return banknotes.stream()
            .map(Banknote::getNominalType)
            .map(NominalType::getNominal)
            .reduce(0, Integer::sum);
    }
}
