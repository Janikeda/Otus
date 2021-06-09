package ru.otus.homework.domain;

public class Banknote {

    private final NominalType nominalType;

    public Banknote(NominalType nominalType) {
        this.nominalType = nominalType;
    }

    public NominalType getNominalType() {
        return nominalType;
    }

    @Override
    public String toString() {
        return "Банкнота {" +
            "номинал=" + nominalType.getNominal() +
            '}';
    }
}
