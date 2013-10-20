package com.example.contactmanager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Class representing the contact list data model. Contains operations for adding, deleting,
 * and modifying contacts. Very dependent on DatabaseHelper, which establishes and manages the
 * database.
 * @author hrob748
 */
public class ContactList {
	
	private Comparator<Contact> _contactComparator;
	
	// Database data
	private SQLiteDatabase _db;
	private DatabaseHelper _dbHelper;
	private String[] _cols = new String[] {
			DatabaseHelper.COL_ID, DatabaseHelper.COL_FIRSTNAME, DatabaseHelper.COL_LASTNAME, DatabaseHelper.COL_HOMEPHONE,
			DatabaseHelper.COL_WORKPHONE, DatabaseHelper.COL_MOBILEPHONE, DatabaseHelper.COL_EMAIL, DatabaseHelper.COL_ADDRESS,
			DatabaseHelper.COL_DOB
	};
	
	
	
	public ContactList(Context context) {
		_dbHelper = new DatabaseHelper(context);
	}
	
	/**
	 * Open the database.
	 */
	public void open() {
		_db = _dbHelper.getWritableDatabase();
	}
	
	/**
	 * Close the database.
	 */
	public void close() {
		_dbHelper.close();
	}
	
	/**
	 * Create a contact and add it to the database.
	 */
	public Contact createContact(String fName, String lName, String hPhone, String wPhone,
			String mPhone, String address, String email, String dob) {
		open();
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COL_FIRSTNAME, fName);
		values.put(DatabaseHelper.COL_LASTNAME, lName);
		values.put(DatabaseHelper.COL_HOMEPHONE, hPhone);
		values.put(DatabaseHelper.COL_WORKPHONE, wPhone);
		values.put(DatabaseHelper.COL_MOBILEPHONE, mPhone);
		values.put(DatabaseHelper.COL_EMAIL, email);
		values.put(DatabaseHelper.COL_ADDRESS, address);
		values.put(DatabaseHelper.COL_DOB, dob);
		//values.put(_dbHelper.COL_IMAGE, image);
		long insertID = _db.insert(DatabaseHelper.TABLE_NAME, null, values);
		values.put(DatabaseHelper.COL_ID, insertID);
		Cursor cursor = _db.query(DatabaseHelper.TABLE_NAME, _cols,
				DatabaseHelper.COL_ID + " = " + insertID, null, null, null, null);
		cursor.moveToFirst();
		Contact newContact = cursorToContact(cursor);
		close();
		return newContact;
	}
	
	/**
	 * Edits the values of a contact.
	 */
	public void editContact(long id, String fName, String lName,
			String email, String hPhone, String mPhone, String wPhone,
			String dob, String image, String address) {
		open();
		final ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COL_ID, id);
		values.put(DatabaseHelper.COL_FIRSTNAME, fName);
		values.put(DatabaseHelper.COL_LASTNAME, lName);
		values.put(DatabaseHelper.COL_HOMEPHONE, hPhone);
		values.put(DatabaseHelper.COL_WORKPHONE, wPhone);
		values.put(DatabaseHelper.COL_MOBILEPHONE, mPhone);
		values.put(DatabaseHelper.COL_EMAIL, email);
		values.put(DatabaseHelper.COL_ADDRESS, address);
		values.put(DatabaseHelper.COL_DOB, dob);
		//values.put(DatabaseHelper.COL_IMAGE, image);
		_db.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COL_ID + "=" + id, null);
		close();
	}
	
	/** 
	 * Deletes a contact from the database.
	 * @param contact = The contact to be deleted.
	 */
	public void deleteContact(Contact contact) {
		open();
		long id = contact.getId();
		_db.delete(DatabaseHelper.TABLE_NAME, "_id = ?", 
				new String[]{Long.toString(id)});
		close();
	}
	
	/**
	 * Returns a list of contact objects as constructed from the database.
	 * @return = a list of all the contacts in the database.
	 */
	public List<Contact> getAllContacts() {
		List<Contact> ls = new ArrayList<Contact>();
		
		Cursor c = _db.query(DatabaseHelper.TABLE_NAME, _cols, null, null, null, 
				null, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Contact contact = cursorToContact(c);
			ls.add(contact);
			c.moveToNext();
		}
		c.close();
		return ls;
	}
	
	public Cursor getAllData() {
		String buildSQL = "SELECT * FROM " + DatabaseHelper.TABLE_NAME;
		return _db.rawQuery(buildSQL, null);
	}
	
	/**
	 * Converts a cursor into a contact object.
	 * I think I can justify it being static because calling it makes
	 * sense even when no contact list exists.
	 * @param cursor = the cursor to be converted
	 * @return = a Contact object
	 */
	public static Contact cursorToContact(Cursor cursor) {
		Contact contact = new Contact();
		contact.setID(cursor.getLong(0));
		contact.setFirstName(cursor.getString(1));
		contact.setLastName(cursor.getString(2));
		contact.setHomePhone(cursor.getString(3));
		contact.setWorkPhone(cursor.getString(4));
		contact.setMobilePhone(cursor.getString(5));
		contact.setEmail(cursor.getString(6));
		contact.setAddress(cursor.getString(7));
		contact.setDOB(cursor.getString(8));
		//contact.setImageId(null);
		return contact;
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
}

