package ru.otus.homework.service;

import java.util.List;
import java.util.Map;
import ru.otus.homework.domain.Banknote;
import ru.otus.homework.domain.NominalType;

public interface CalculationService {

    long calculateBankAccount(Map<NominalType, List<Banknote>> bankAccount);

    List<Banknote> getMoney(long amount, Map<NominalType, List<Banknote>> bankAccount);
}
