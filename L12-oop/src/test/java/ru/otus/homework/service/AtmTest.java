package ru.otus.homework.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.config.AppConfig;
import ru.otus.homework.domain.Banknote;
import ru.otus.homework.domain.NominalType;
import ru.otus.homework.dto.BalanceAmount;
import ru.otus.homework.exception.AtmException;

class AtmTest {

    private static final List<NominalType> NOMINAL_TYPES = Arrays.stream(NominalType.values())
        .collect(Collectors.toList());

    private static Atm atm;
    private static Random rand;
    private List<Banknote> banknotes = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        atm = AppConfig.atm();
        rand = new Random();
    }

    @BeforeEach
    void beforeEach() {
        for (int i = 0; i < 50; i++) {
            int randomIndex = rand.nextInt(NOMINAL_TYPES.size());
            NominalType nominalType = NOMINAL_TYPES.get(randomIndex);
            Banknote banknote = new Banknote(nominalType);
            banknotes.add(banknote);
        }
    }

    @Test
    void putMoney() {
        atm.putMoney(banknotes);
        BalanceAmount balanceAmount = atm.checkBalanceAmount();
        assertNotNull(balanceAmount);
    }

    @Test
    void getMoney() {
        atm.putMoney(banknotes);
        BalanceAmount balanceAmount1 = atm.checkBalanceAmount();
        List<Banknote> money = atm.getMoney(100);
        BalanceAmount balanceAmount2 = atm.checkBalanceAmount();
        assertTrue(balanceAmount1.getAmount() > balanceAmount2.getAmount());
        assertSame(money.get(0).getNominalType(), NominalType.ONE_HUNDRED);
    }

    @Test
    void getMoney2() {
        atm.putMoney(banknotes);
        BalanceAmount balanceAmount1 = atm.checkBalanceAmount();
        List<Banknote> money = atm.getMoney(50);
        BalanceAmount balanceAmount2 = atm.checkBalanceAmount();
        assertTrue(balanceAmount1.getAmount() > balanceAmount2.getAmount());
        assertSame(money.get(0).getNominalType(), NominalType.FIFTY);
    }

    @Test
    void getMoney3() {
        atm.putMoney(banknotes);
        assertThrows(AtmException.class, () -> atm.getMoney(0),
            "Сумма к выдаче должна быть больше 0");
    }

    @Test
    void getMoney4() {
        atm.putMoney(banknotes);
        assertThrows(AtmException.class, () -> atm.getMoney(33),
            "Сумма к выдаче должна быть кратна 5");
    }
}
