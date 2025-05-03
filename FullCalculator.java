import java.util.Scanner;

public class FullCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== CALCULATOR MENU =====");
            System.out.println("1. Basic Arithmetic");
            System.out.println("2. Scientific Calculator");
            System.out.println("3. Unit Conversion");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1: basicArithmetic(scanner); break;
                case 2: scientificCalculator(scanner); break;
                case 3: unitConversion(scanner); break;
                case 0: System.out.println("Exiting program."); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);

        scanner.close();
    }

    // Basic arithmetic operations
    static void basicArithmetic(Scanner sc) {
        System.out.print("Enter first number: ");
        double a = sc.nextDouble();
        System.out.print("Enter operator (+, -, *, /): ");
        char op = sc.next().charAt(0);
        System.out.print("Enter second number: ");
        double b = sc.nextDouble();
        double result = 0;

        switch (op) {
            case '+': result = a + b; break;
            case '-': result = a - b; break;
            case '*': result = a * b; break;
            case '/':
                if (b != 0) result = a / b;
                else {
                    System.out.println("Error: Division by zero!");
                    return;
                }
                break;
            default: System.out.println("Invalid operator."); return;
        }

        System.out.println("Result: " + result);
    }

    // Scientific operations
    static void scientificCalculator(Scanner sc) {
        System.out.println("1. Square Root");
        System.out.println("2. Exponentiation (x^y)");
        System.out.print("Choose operation: ");
        int op = sc.nextInt();
        double result = 0;

        switch (op) {
            case 1:
                System.out.print("Enter number: ");
                double x = sc.nextDouble();
                result = Math.sqrt(x);
                break;
            case 2:
                System.out.print("Enter base: ");
                double base = sc.nextDouble();
                System.out.print("Enter exponent: ");
                double exp = sc.nextDouble();
                result = Math.pow(base, exp);
                break;
            default: System.out.println("Invalid option."); return;
        }

        System.out.println("Result: " + result);
    }

    // Unit conversion operations
    static void unitConversion(Scanner sc) {
        System.out.println("1. Temperature");
        System.out.println("2. Currency");
        System.out.print("Choose conversion type: ");
        int type = sc.nextInt();

        switch (type) {
            case 1: temperatureConversion(sc); break;
            case 2: currencyConversion(sc); break;
            default: System.out.println("Invalid option.");
        }
    }

    static void temperatureConversion(Scanner sc) {
        System.out.println("1. Celsius to Fahrenheit");
        System.out.println("2. Fahrenheit to Celsius");
        System.out.println("3. Celsius to Kelvin");
        System.out.print("Choose conversion: ");
        int opt = sc.nextInt();
        System.out.print("Enter temperature: ");
        double temp = sc.nextDouble();
        double result = 0;

        switch (opt) {
            case 1: result = (temp * 9/5) + 32; break;
            case 2: result = (temp - 32) * 5/9; break;
            case 3: result = temp + 273.15; break;
            default: System.out.println("Invalid choice."); return;
        }

        System.out.println("Converted: " + result);
    }

    static void currencyConversion(Scanner sc) {
        System.out.println("1. USD to EUR");
        System.out.println("2. EUR to USD");
        System.out.println("3. USD to INR");
        System.out.println("4. INR to USD");
        System.out.print("Choose conversion: ");
        int opt = sc.nextInt();
        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();

        double result = 0;
        switch (opt) {
            case 1: result = amount * 0.91; break;        // 1 USD = 0.91 EUR
            case 2: result = amount / 0.91; break;        // 1 EUR = 1.10 USD
            case 3: result = amount * 83.5; break;        // 1 USD = 83.5 INR
            case 4: result = amount / 83.5; break;        // 1 INR = 0.012 USD
            default: System.out.println("Invalid option."); return;
        }

        System.out.println("Converted amount: " + result);
    }
}
