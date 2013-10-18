package com.example.contactmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactsDatabaseHelper extends SQLiteOpenHelper {

	// DatabaseHandler should only really exist once
	public static ContactsDatabaseHelper singleton;
	
	private static final String DATABASE_NAME = "contactsDatabase"; 
	private static final int DATABASE_VERSION = 1;
	
	private final Context context;
	
	public static ContactsDatabaseHelper getInstance(final Context context) {
		if (singleton == null) {
			singleton = new ContactsDatabaseHelper(context);
		}
		return singleton;
	}
	
	public ContactsDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// Not sure whether this is necessary for Android. Keeps context when window is closed.
		this.context = context.getApplicationContext();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Contact.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	/**
	 * Get all the data in the form of a database cursor.
	 * @return = cursor with all data from database
	 */
	public Cursor getAllData() {
		String buildSQL = "SELECT * FROM " + Contact.TABLE_NAME;
		return this.getReadableDatabase().rawQuery(buildSQL, null);
	}
	
	/**
	 * Gets a contact from the database
	 * @param id = the Contact's ID
	 * @return a Contact
	 */
	public synchronized Contact getContact(final long id) {
		final SQLiteDatabase db = this.getReadableDatabase();
		final Cursor cursor = db.query(Contact.TABLE_NAME, Contact.FIELDS, Contact.COL_ID + 
				" IS ?", new String[]{String.valueOf(id)}, null, null, null, null);
		if (cursor == null || cursor.isAfterLast()) {
			return null;
		}
		Contact item = null;
		if (cursor.moveToFirst()) {
			item = new Contact(cursor);
		}
		cursor.close();
		return item;
	}
	
	/**
	 * Inserts a contact into the database
	 * @param c = a Contact to be inserted
	 * @return = true or false depending on operation success
	 */
	public synchronized boolean putContact(final Contact c) {
		boolean isSuccess = false;
		int result = 0;
		final SQLiteDatabase db = this.getWritableDatabase();
		
		if (c.id > -1) {
			result += db.update(Contact.TABLE_NAME, c.getContent(), c.COL_ID
					+ " IS ?", new String[]{String.valueOf(c.id)});
		}
		
		if (result > 0) {
			isSuccess = true;
		} else {
			// failed update
			final long id = db.insert(Contact.TABLE_NAME, null, c.getContent());
			
			if (id > -1) {
				c.id = id;
				isSuccess = true;
			}
		}
		
		if (isSuccess) {
			notifyProviderOnContactChange();
		}
		return isSuccess;
	}
	
	public synchronized int removeContact(final Contact c) {
		final SQLiteDatabase db = this.getWritableDatabase();
		final int result = db.delete(Contact.TABLE_NAME, Contact.COL_ID + " IS ?",
				new String[]{Long.toString(c.id)});
		
		if (result > 0) {
			notifyProviderOnContactChange();
		}
		return result;
	}
	
	private void notifyProviderOnContactChange() {
		context.getContentResolver().notifyChange(ContactProvider.URI_CONTACTS,
				null, false);
	}

}
