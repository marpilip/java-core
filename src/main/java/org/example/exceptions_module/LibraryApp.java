package org.example.exceptions_module;


import org.example.exceptions_module.exceptions.ItemNotFoundException;
import org.example.exceptions_module.exceptions.NoAvailableCopiesException;

import java.util.Scanner;

public class LibraryApp {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    Меню библиотеки:
                    1. Вывести каталог
                    2. Добавить книгу
                    3. Взять книгу
                    4. Вернуть книгу
                    5. Выйти
                    Выберите опцию:\s""");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                String title;

                switch (choice) {
                    case 1:
                        library.displayCatalog();
                        break;

                    case 2:
                        System.out.print("Введите название: ");
                        title = scanner.nextLine();
                        System.out.print("Введите автора: ");
                        String author = scanner.nextLine();
                        System.out.print("Введите количество: ");
                        int copies = scanner.nextInt();
                        scanner.nextLine();
                        library.addBook(title, author, copies);
                        break;

                    case 3:
                        System.out.print("Введите название книги для взятия: ");
                        title = scanner.nextLine();
                        try {
                            library.takeBook(title);
                            System.out.println("Книга взята: " + title);
                        } catch (ItemNotFoundException | NoAvailableCopiesException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.print("Введите название книги для возврата: ");
                        title = scanner.nextLine();
                        try {
                            library.returnBook(title);
                            System.out.println("Книга возвращена: " + title);
                        } catch (ItemNotFoundException e) {
                            System.out.println(e.getMessage());
                        }

                        break;

                    case 5:
                        System.out.println("Выход из приложения...");
                        return;

                    default:
                        System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Неверный ввод. Пожалуйста, введите число.");
                scanner.nextLine();
            }
        }
    }
}
