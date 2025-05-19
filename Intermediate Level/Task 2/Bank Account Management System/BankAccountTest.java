import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount account = new BankAccount("Alice");
        account.deposit(100.0);
        System.out.println("Deposit Test: Balance after depositing 100.0 = " + account.getBalance());
        assertEquals(100.0, account.getBalance());
    }

    @Test
    public void testWithdraw() {
        BankAccount account = new BankAccount("Bob");
        account.deposit(200.0);
        account.withdraw(50.0);
        System.out.println("Withdraw Test: Balance after withdrawing 50.0 = " + account.getBalance());
        assertEquals(150.0, account.getBalance());
    }

    @Test
    public void testInsufficientFunds() {
        BankAccount account = new BankAccount("Charlie");
        account.deposit(100.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(200.0);
        });
        System.out.println("Insufficient Funds Test: Attempted to withdraw 200.0 from 100.0 balance");
        assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    public void testNegativeDeposit() {
        BankAccount account = new BankAccount("Dave");
        System.out.println("Negative Deposit Test: Attempting to deposit -50.0");
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-50);
        });
    }

    @Test
    public void testTransactionHistory() {
        BankAccount account = new BankAccount("Eve");
        account.deposit(300);
        account.withdraw(100);
        System.out.println("Transaction History Test: " + account.getTransactions());
        assertEquals(2, account.getTransactions().size());
    }

    @Test
    public void testApplyInterest() {
        BankAccount account = new BankAccount("Grace");
        account.deposit(1000);
        account.applyMonthlyInterest(12); // 12% annual interest → 1% monthly
        System.out.println("Apply Interest Test:");
        System.out.println("Balance after interest = $" + account.getBalance());
        account.getTransactions().forEach(System.out::println);
        assertTrue(account.getBalance() > 1000);
    }

    @Test
    public void testMonthlyStatement() {
        BankAccount account = new BankAccount("Frank");
        account.deposit(400);
        account.withdraw(200);
        account.applyMonthlyInterest(12); // 12% annual interest → 1% monthly

        System.out.println("Monthly Statement Test:");
        account.printMonthlyStatement(LocalDate.now().getMonthValue(), LocalDate.now().getYear());

        List<Transaction> statement = account.getMonthlyStatement(
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear()
        );
        assertEquals(3, statement.size());
    }

    @Test
    public void testAccountNumberUniqueness() {
        BankAccount a1 = new BankAccount("User1");
        BankAccount a2 = new BankAccount("User2");

        System.out.println("Account Number Test:");
        System.out.println("User1 Account Number: " + a1.getAccountNumber());
        System.out.println("User2 Account Number: " + a2.getAccountNumber());

        assertNotEquals(a1.getAccountNumber(), a2.getAccountNumber());
    }
}
