package org.example.collections;

import org.example.collections.exceptions.ContactAlreadyExistsException;
import org.example.collections.exceptions.ContactNotFoundException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContactBook contactBook = new ContactBook();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    Меню контактной книги:
                    1: Добавить контакт
                    2: Удалить контакт
                    3: Посмотреть все контакты
                    4: Найти контакт
                    5: Посмотреть контакты по группе
                    0: Выход
                    Выберите команду:\s""");
            int command = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (command) {
                    case 1:
                        System.out.print("Введите имя: ");
                        String name = scanner.nextLine();
                        System.out.print("Введите телефон: ");
                        String phone = scanner.nextLine();
                        System.out.print("Введите email: ");
                        String email = scanner.nextLine();
                        System.out.print("Введите группу: ");
                        String group = scanner.nextLine();
                        contactBook.addContact(new Contact(name, phone, email, group));
                        break;

                    case 2:
                        System.out.print("Введите имя контакта для удаления: ");
                        String removeName = scanner.nextLine();
                        contactBook.removeContact(removeName);
                        break;

                    case 3:
                        contactBook.viewAllContacts();
                        break;

                    case 4:
                        System.out.print("Введите имя контакта для поиска: ");
                        String searchName = scanner.nextLine();
                        contactBook.searchContact(searchName);
                        break;

                    case 5:
                        System.out.print("Введите название группы: ");
                        String searchGroup = scanner.nextLine();
                        contactBook.viewContactsByGroup(searchGroup);
                        break;

                    case 0:
                        System.out.println("Выход из программы");
                        return;

                    default:
                        System.out.println("Неверная команда.");
                }
            } catch (ContactAlreadyExistsException | ContactNotFoundException e) {
                System.out.println("Ошибка: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Произошла непредвиденная ошибка: " + e.getMessage());
            }
        }
    }
}