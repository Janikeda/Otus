package ru.otus.homework.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import ru.otus.homework.domain.Banknote;
import ru.otus.homework.domain.NominalType;
import ru.otus.homework.dto.BalanceAmount;

public class AtmUsageService {

    private static final List<NominalType> NOMINAL_TYPES = Arrays.stream(NominalType.values())
        .collect(Collectors.toList());

    private final Atm atm;
    private List<Banknote> account;

    public AtmUsageService(Atm atm, int accountSize) {
        this.atm = atm;
        postConstruct(accountSize);
    }

    private void postConstruct(int accountSize) {
        Random rand = new Random();
        this.account = new ArrayList<>();
        for (int i = 0; i < accountSize; i++) {
            int randomIndex = rand.nextInt(NOMINAL_TYPES.size());
            NominalType nominalType = NOMINAL_TYPES.get(randomIndex);
            Banknote banknote = new Banknote(nominalType);
            account.add(banknote);
        }
        putMoney();
    }

    public void putMoney(List<Banknote> money) {
        atm.putMoney(money);
        BalanceAmount balanceAmount = atm.checkBalanceAmount();
        System.out.println("Счет пополнен. На счету: " + balanceAmount.getAmount());
        System.out.println("--------------------------\n");
    }

    public void getMoney(long amount) {
        System.out.println("Снять деньги, сумма: " + amount);
        List<Banknote> money = atm.getMoney(amount);
        System.out.println(money);
        BalanceAmount balanceAmount = atm.checkBalanceAmount();
        System.out.println("На счету: " + balanceAmount.getAmount());
        System.out.println("--------------------------\n");
    }

    private void putMoney() {
        atm.putMoney(account);
        BalanceAmount balanceAmount = atm.checkBalanceAmount();
        System.out.println("Счет открыт. Баланс: " + balanceAmount.getAmount());
        System.out.println("--------------------------\n");
    }
}
