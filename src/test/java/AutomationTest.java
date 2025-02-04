import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.dragon.controller.Bank;
import org.dragon.object.Debt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AutomationTest {
    @Test
    @DisplayName("test case 1")
    public void testCase1() {
        Bank bank = new Bank();
        bank.login("alice");
        bank.deposit(100);
        bank.logout();
        bank.login("bob");
        bank.deposit(80);
        bank.transfer("alice", 50);
        assertEquals(30, bank.getActiveAccount().getBalance());
        bank.transfer("alice", 100);
        assertEquals(0, bank.getActiveAccount().getBalance());
        List<Debt> debtFilter = bank.getDebtList().stream().filter(debt -> debt.getTarget().equals("alice")).collect(Collectors.toList());
        assertEquals(70, debtFilter.get(0).getAmount());
        bank.deposit(30);
        assertEquals(0, bank.getActiveAccount().getBalance());
        debtFilter = bank.getDebtList().stream().filter(debt -> debt.getTarget().equals("alice")).collect(Collectors.toList());
        assertEquals(40, debtFilter.get(0).getAmount());
        bank.logout();
        bank.login("alice");
        assertEquals(210, bank.getActiveAccount().getBalance());
        debtFilter = bank.getDebtList().stream().filter(debt -> debt.getFrom().equals("bob")).collect(Collectors.toList());
        assertEquals(40, debtFilter.get(0).getAmount());
        bank.transfer("bob", 30);
        debtFilter = bank.getDebtList().stream().filter(debt -> debt.getFrom().equals("bob")).collect(Collectors.toList());
        assertEquals(210, bank.getActiveAccount().getBalance());
        assertEquals(10, debtFilter.get(0).getAmount());
        bank.logout();
        bank.login("bob");
        assertEquals(0, bank.getActiveAccount().getBalance());
        debtFilter = bank.getDebtList().stream().filter(debt -> debt.getTarget().equals("alice")).collect(Collectors.toList());
        assertEquals(10, debtFilter.get(0).getAmount());
        bank.deposit(100);
        assertEquals(90, bank.getActiveAccount().getBalance());
        bank.logout();
        bank.login("alice");
        assertEquals(220, bank.getActiveAccount().getBalance());
    }
}
