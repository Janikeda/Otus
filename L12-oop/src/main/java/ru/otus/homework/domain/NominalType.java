package ru.otus.homework.domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum NominalType {
    FIVE(5), TEN(10), FIFTY(50), ONE_HUNDRED(100);

    private final Integer nominal;

    NominalType(int nominal) {
        this.nominal = nominal;
    }

    public Integer getNominal() {
        return this.nominal;
    }

    public static List<NominalType> getSortedNominals() {
        return Arrays.stream(values())
            .sorted(Comparator.comparing(NominalType::getNominal, Comparator.reverseOrder()))
            .collect(Collectors.toList());
    }
}
