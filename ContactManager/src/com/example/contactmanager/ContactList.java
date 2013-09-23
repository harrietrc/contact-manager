package com.example.contactmanager;

import java.util.ArrayList;

public class ContactList {
	
	// Singleton stuff
	private static ContactList singletonInstance = null;
	
	public static ContactList getContactList() {
		if (singletonInstance == null) {
			singletonInstance = new ContactList();
		} 
		return singletonInstance;
	}
	
	public static ArrayList<Contact> _contacts;
	
	private ContactList() {
		_contacts = new ArrayList<Contact>();
		mockList(); // Yes, bad practice, I know.
	}
	
	public void mockList() {
		for (int i=0; i<5; i++) {
			_contacts.add(new Contact());
		}
	}
	
	public static void addContact() {
		_contacts.add(new Contact());
	}
	
	public static void deleteContact() {
		
	}

	public int size() {
		return _contacts.size();
	}
}

