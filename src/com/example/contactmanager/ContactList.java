package com.example.contactmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Class representing the contact list data model. Contains operations for
 * adding, deleting, and modifying contacts. Very dependent on DatabaseHelper,
 * which establishes and manages the database.
 * 
 * @author Harriet Robinson-Chen
 */
public class ContactList {

	// Database data
	private SQLiteDatabase _db;
	private static DatabaseHelper _dbHelper;
	private String[] _cols = new String[] { DatabaseHelper.COL_ID,
			DatabaseHelper.COL_FIRSTNAME, DatabaseHelper.COL_LASTNAME,
			DatabaseHelper.COL_HOMEPHONE, DatabaseHelper.COL_WORKPHONE,
			DatabaseHelper.COL_MOBILEPHONE, DatabaseHelper.COL_EMAIL,
			DatabaseHelper.COL_ADDRESS, DatabaseHelper.COL_DOB,
			DatabaseHelper.COL_IMAGE };

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
	 * Returns a cursor over the contacts matching a search term. Not case
	 * sensitive, and will match names that begin with the search term. Matches
	 * first + last name (concatenated together
	 * 
	 * @param term
	 *            = the search term
	 * @return = cursor with matching contacts
	 */
	public Cursor getMatch(String term) {
		String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME;

		// Blank search terms should return all contacts. Else, perform the
		// search.
		if (term.length() != 0) {
			String formattedTerm = term.replaceAll("\\s+","").replace("'","\'").toUpperCase();
			query += " WHERE upper(firstName)||upper(lastName) LIKE '" + 
			formattedTerm + "%' OR upper(firstName) LIKE '" + formattedTerm 
			+ "%' OR upper(lastName) LIKE '" + formattedTerm + "%'";
		}
		Cursor cursor = _db.rawQuery(query, null);

		if (cursor != null) {
			cursor.moveToFirst();
			return cursor;
		}

		return null;
	}

	/**
	 * Gets a contact by its corresponding ID.
	 * 
	 * @param id = the ID of the required contact.
	 * @return = a Contact object corresponding to the ID.
	 */
	public Contact getContactByID(long id) {
		SQLiteDatabase db = _dbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM "
				+ DatabaseHelper.TABLE_NAME + " WHERE _id = " + id, null);
		cursor.moveToFirst();
		Contact contact = ContactList.cursorToContact(cursor);
		contact.setID(id);
		return contact;
	}

	/**
	 * Close the database.
	 */
	public void close() {
		_dbHelper.close();
	}

	/**
	 * Create a contact and add its data to the database.
	 */
	public Contact createContact(String fName, String lName, String hPhone,
			String wPhone, String mPhone, String address, String email,
			String dob, byte[] image) {
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
		values.put(DatabaseHelper.COL_IMAGE, image);
		long insertID = _db.insert(DatabaseHelper.TABLE_NAME, null, values);
		values.put(DatabaseHelper.COL_ID, insertID);
		Cursor cursor = _db.query(DatabaseHelper.TABLE_NAME, _cols,
				DatabaseHelper.COL_ID + " = " + insertID, null, null, null,
				null);
		cursor.moveToFirst();
		Contact newContact = cursorToContact(cursor);
		close();
		return newContact;
	}

	/**
	 * Creates a new contact and initialises its fields to empty strings.
	 * 
	 * @return = new blank contact.
	 */
	public Contact newContact() {
		return createContact("", "", "", "", "", "", "", "", null);
	}

	/**
	 * Edits the values of a contact.
	 */
	public void editContact(long id, String fName, String lName, String email,
			String hPhone, String mPhone, String wPhone, String dob,
			byte[] image, String address) {
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
		values.put(DatabaseHelper.COL_IMAGE, image);
		_db.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COL_ID
				+ "=" + id, null);
		close();
	}

	/**
	 * Deletes a contact from the database.
	 * 
	 * @param contact = The contact to be deleted.
	 */
	public void deleteContact(Contact contact) {
		open();
		long id = contact.getId();
		_db.delete(DatabaseHelper.TABLE_NAME, "_id = ?",
				new String[] { Long.toString(id) });
		close();
	}

	/**
	 * Given a contact ID, deletes a contact from the database that matches that
	 * ID.
	 * 
	 * @param id = the ID of the contact.
	 */
	public void deleteContactByID(long id) {
		open();
		_db.delete(DatabaseHelper.TABLE_NAME, "_id = ?",
				new String[] { Long.toString(id) });
		close();
	}

	/**
	 * Gets all the data in the database as a cursor. Sort order can be
	 * specified using the column name to be sorted by.
	 * 
	 * @param sortOrder = the DatabaseHelper column name to be sorted by.
	 * @return
	 */
	public Cursor getAllData(String sortOrder) {
		String buildSQL = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " "
				+ sortOrder;
		return _db.rawQuery(buildSQL, null);
	}

	/**
	 * Converts a cursor into a contact object. I think I can justify it being
	 * static because calling it makes sense even when no contact list exists.
	 * 
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
		contact.setImage(cursor.getBlob(9));
		return contact;
	}

}
