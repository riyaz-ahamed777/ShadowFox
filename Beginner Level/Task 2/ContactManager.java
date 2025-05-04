import java.util.ArrayList;
import java.util.Scanner;

public class ContactManager {
    private static ArrayList<Contact> contacts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Contact Management System ---");
            System.out.println("1. Add Contact");
            System.out.println("2. View All Contacts");
            System.out.println("3. Update Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    updateContact();
                    break;
                case 4:
                    deleteContact();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // Add a new contact
    private static void addContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        // Basic validation (optional)
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            System.out.println("Error: Fields cannot be empty!");
            return;
        }

        contacts.add(new Contact(name, phone, email));
        System.out.println("Contact added successfully!");
    }

    // View all contacts
    private static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    // Update a contact
    private static void updateContact() {
        viewContacts();
        if (contacts.isEmpty()) return;

        System.out.print("Enter the index of the contact to update: ");
        int index = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid index!");
            return;
        }

        Contact contact = contacts.get(index);
        System.out.print("Enter new name (leave blank to keep current): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) contact.setName(name);

        System.out.print("Enter new phone (leave blank to keep current): ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) contact.setPhone(phone);

        System.out.print("Enter new email (leave blank to keep current): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) contact.setEmail(email);

        System.out.println("Contact updated successfully!");
    }

    // Delete a contact
    private static void deleteContact() {
        viewContacts();
        if (contacts.isEmpty()) return;

        System.out.print("Enter the index of the contact to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid index!");
            return;
        }

        contacts.remove(index);
        System.out.println("Contact deleted successfully!");
    }
}