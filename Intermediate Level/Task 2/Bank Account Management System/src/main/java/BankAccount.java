import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

public class BankAccount {
    private static int nextAccountNumber = 1000;

    private String accountHolder;
    private final int accountNumber;
    private double balance;
    private List<Transaction> transactions;

    public BankAccount(String accountHolder) {
        this.accountHolder = accountHolder;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
        this.accountNumber = nextAccountNumber++;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (amount > balance) throw new IllegalArgumentException("Insufficient funds");
        balance -= amount;
        transactions.add(new Transaction("Withdraw", amount));
    }

    public void applyMonthlyInterest(double annualRatePercent) {
        double monthlyRate = annualRatePercent / 12 / 100;
        double interest = balance * monthlyRate;
        if (interest > 0) {
            balance += interest;
            transactions.add(new Transaction("Interest", interest));
        }
    }

    public List<Transaction> getMonthlyStatement(int month, int year) {
        return transactions.stream()
                .filter(t -> t.getTimestamp().getMonthValue() == month &&
                        t.getTimestamp().getYear() == year)
                .collect(Collectors.toList());
    }

    public void printMonthlyStatement(int month, int year) {
        System.out.println("Monthly Statement for " + accountHolder + " (Account #" + accountNumber + ")");
        List<Transaction> statement = getMonthlyStatement(month, year);
        for (Transaction t : statement) {
            System.out.println(t);
        }
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions); // Encapsulation
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }
}
