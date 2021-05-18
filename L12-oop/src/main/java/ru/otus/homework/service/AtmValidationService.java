package ru.otus.homework.service;

import java.util.List;
import java.util.Map;
import ru.otus.homework.domain.Banknote;
import ru.otus.homework.domain.NominalType;

public interface AtmValidationService {
    void checkAmount(long amount, Map<NominalType, List<Banknote>> bankAccount);
}
