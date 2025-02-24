package org.example.phoneBook;

import java.util.Scanner;

public class ContactManager {
    private static final int MAX_CONTACTS = 100;
    private final String[] names = new String[MAX_CONTACTS];
    private final String[] phoneNumbers = new String[MAX_CONTACTS];
    private int contactCount = 0;
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ContactManager manager = new ContactManager();
        manager.run();
    }

    public void run() {
        while (true) {
            printMenu();

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addContact();
                    break;

                case 2:
                    viewContacts();
                    break;

                case 3:
                    searchContact();
                    break;

                case 4:
                    deleteContact();
                    break;

                case 5:
                    System.out.println("Выход из программы...");
                    return;

                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");

            }
        }
    }

    private void printMenu() {
        System.out.println("""
                Меню для работы с приложением:
                1. Добавить контакт
                2. Просмотреть контакты
                3. Найти контакт
                4. Удалить контакт
                5. Выйти
                Выберите опцию с помощью цифры:\s""");
    }

    private void addContact() {
        if (contactCount >= MAX_CONTACTS) {
            System.out.println("Записная книга переполнена. Невозможно добавить еще контакт.");
            return;
        }

        System.out.println("Введите имя: ");
        String name = scanner.nextLine();
        names[contactCount] = name;

        System.out.println("Введите номер телефона (в виде 11 цифр): ");
        String phoneNumber = scanner.nextLine();
        phoneNumbers[contactCount] = phoneNumber;

        System.out.println("Контакт добавлен.");
        contactCount++;
    }

    private void viewContacts() {
        if (contactCount == 0) {
            System.out.println("Контакты не найдены. Книга пуста");
            return;
        }

        System.out.println("Список контактов: ");

        for (int i = 0; i < contactCount; i++) {
            System.out.println((i + 1) + ". Имя: " + names[i] + " - " + phoneNumbers[i]);
        }
    }

    private void searchContact() {
        System.out.print("Введите имя для поиска контакта: ");
        String name = scanner.nextLine();

        for (int i = 0; i < contactCount; i++) {
            if (names[i].equalsIgnoreCase(name)) {
                System.out.println("Телефон " + names[i] + ": " + phoneNumbers[i]);
                return;
            }
        }

        System.out.println("Контакт с именем " + name + " не найден.");
    }

    private void deleteContact() {
        System.out.println("Введите имя для удаления контакта: ");
        String name = scanner.nextLine();

        for (int i = 0; i < contactCount; i++) {
            if (names[i].equalsIgnoreCase(name)) {
                for (int j = i; j < contactCount - 1; j++) {
                    phoneNumbers[j] = phoneNumbers[j + 1];
                    names[j] = names[j + 1];
                }

                System.out.println("Контакт удалён.");
                contactCount--;
                return;
            }
        }

        System.out.println("Контакт с именем " + name + " не найден.");
    }
}