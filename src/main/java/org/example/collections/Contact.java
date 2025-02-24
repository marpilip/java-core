package org.example.collections;

import java.util.Objects;

public class Contact {
    private final String name;
    private final String phone;
    private final String email;
    private final String group;

    public Contact(String name, String phone, String email, String group) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) && Objects.equals(phone, contact.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone);
    }

    @Override
    public String toString() {
        return "Контакт: " + name +
                "\n Номер телефона: " + phone +
                "\n email: " + email +
                "\n группа: " + group;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }
}
