package org.example.collections;

import org.example.collections.exceptions.ContactAlreadyExistsException;
import org.example.collections.exceptions.ContactNotFoundException;
import org.example.collections.exceptions.GroupNotFoundException;

import java.util.*;

public class ContactBook {
    private final List<Contact> contactList;
    private final Set<Contact> contactSet;
    private final Map<String, List<Contact>> groupMap;

    public ContactBook() {
        contactList = new ArrayList<>();
        contactSet = new HashSet<>();
        groupMap = new HashMap<>();
    }

    public void addContact(Contact contact) throws ContactAlreadyExistsException {
        if (!contactSet.add(contact)) {
            throw new ContactAlreadyExistsException("Контакт уже существует: " + contact.getName());
        }

        contactList.add(contact);
        groupMap.computeIfAbsent(contact.getGroup(), k -> new ArrayList<>()).add(contact);
        System.out.println("Контакт добавлен: " + contact);
    }

    public void removeContact(String name) throws ContactNotFoundException {
        Iterator<Contact> iterator = contactList.iterator();

        while (iterator.hasNext()) {
            Contact contact = iterator.next();

            if (contact.getName().equals(name)) {
                iterator.remove();
                contactSet.remove(contact);
                groupMap.get(contact.getGroup()).remove(contact);

                System.out.println("Контакт удален: " + name);
                return;
            }
        }

        throw new ContactNotFoundException("Контакт не найден: " + name);
    }

    public void viewAllContacts() {
        Iterator<Contact> iterator = contactList.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public void searchContact(String name) throws ContactNotFoundException {
        for (Contact contact : contactList) {
            if (contact.getName().equals(name)) {
                System.out.println(contact);
                return;
            }
        }

        throw new ContactNotFoundException("Контакт не найден: " + name);
    }

    public void viewContactsByGroup(String group) throws GroupNotFoundException {
        List<Contact> contacts = groupMap.get(group);

        if (contacts == null || contacts.isEmpty()) {
            throw new GroupNotFoundException("Группа не найдена или пуста: " + group);
        }

        Iterator<Contact> iterator = contacts.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}