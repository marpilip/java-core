package org.example.exceptions_module;

import org.example.exceptions_module.exceptions.ItemNotFoundException;
import org.example.exceptions_module.exceptions.NoAvailableCopiesException;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private final List<Book> catalog;

    public Library() {
        catalog = new ArrayList<>();
    }

    /**
     * Добавляет новую книгу в каталог.
     *
     * @param title  Название книги.
     * @param author Автор книги.
     * @param copies Количество доступных экземпляров.
     */
    public void addBook(String title, String author, int copies) {
        if (copies < 0) {
            throw new IllegalArgumentException("Количество экземпляров не может быть отрицательным.");
        }

        Book book = new Book(title, author, copies);
        catalog.add(book);
        System.out.println("Книга добавлена: " + book);
    }

    public void displayCatalog() {
        for (Book book : catalog) {
            System.out.println(book);
        }
    }

    /**
     * Ищет книгу по названию.
     *
     * @param title Название книги.
     * @return Найденная книга или null, если книга не найдена.
     */
    private Book findBookByTitle(String title) {
        for (Book book : catalog) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }

        return null;
    }

    /**
     * Возвращает книгу.
     *
     * @param title Название книги.
     * @throws ItemNotFoundException Если книга не найдена.
     */
    public void returnBook(String title) throws ItemNotFoundException {
        Book book = findBookByTitle(title);

        if (book == null) {
            throw new ItemNotFoundException("Книга не найдена: " + title);
        }

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        System.out.println("Книга возвращена: " + title);
    }

    /**
     * Выдает книгу.
     *
     * @param title Название книги.
     * @throws ItemNotFoundException      Если книга не найдена.
     * @throws NoAvailableCopiesException Если нет доступных экземпляров.
     */
    public void takeBook(String title) throws ItemNotFoundException, NoAvailableCopiesException {
        Book book = findBookByTitle(title);

        if (book == null) {
            throw new ItemNotFoundException("Книга не найдена: " + title);
        }

        if (book.getAvailableCopies() <= 0) {
            throw new NoAvailableCopiesException("Нет доступных экземпляров для книги: " + title);
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        System.out.println("Книга выдана: " + title);
    }
}
