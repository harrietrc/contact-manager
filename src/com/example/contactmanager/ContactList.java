package com.example.contactmanager;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Having this as a singleton sort of makes sense in that multiple activities are going to need to access it 
 * (DisplayContact to display the correct contact, MainActivity for obvious reasons) and it will only need to be
 * instantiated once. I might change this, however, as singletons seem to considered bad design in every context
 * except logging.
 * @author hrob748
 *
 */
public class ContactList {
	
	public static ArrayList<Contact> _contacts;
	private Comparator<Contact> _contactComparator;
	
	// Singleton stuff
	private static ContactList singletonInstance = null;
	
	/**
	 * Returns the instance of the ContactList and prevents it from
	 * being instantiated more than once.
	 * @return = the ContactList object
	 */
	public static ContactList getContactList() {
		if (singletonInstance == null) {
			singletonInstance = new ContactList();
		} 
		return singletonInstance;
	}
	
	private ContactList() {
		_contacts = new ArrayList<Contact>();
		mockList(); /* TEMPORARY */
	}
	
	/* TEMPORARY */
	public void mockList() {
		for (int i=0; i<5; i++) {
			_contacts.add(new Contact());
		}
	}
	
	/**
	 * Sorts the contacts using the object's comparator.
	 */
	public static void sortContacts() {
		
	}
	
	/**
	 * Sets the comparator to sort by either first name, last name, or number.
	 */
	public static void setComparator() {
		
	}
	
	/**
	 * Adds a contact to the contact list.
	 */
	public static void addContact() {
		_contacts.add(new Contact()); /* May not work like this in final implementation, but plausible enough */
	}
	
	/**
	 * Deletes a specific contact from the contact list.
	 */
	public static void deleteContact() {
		
	}

	/**
	 * Returns the number of contacts in the contact list.
	 * @return = the length of the contact list.
	 */
	public int size() {
		return _contacts.size();
	}
}

