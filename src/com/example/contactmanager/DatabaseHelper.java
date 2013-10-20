package com.example.contactmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "Contact";
	public static final String DB_NAME = "ContactDatabase";
	public static final String DB_TABLE = "cTable";
	public static final int DB_VERSION = 1;
	public static final String COL_ID = "_id";
	public static final String COL_FIRSTNAME = "firstName";
	public static final String COL_LASTNAME = "lastName";
	public static final String COL_HOMEPHONE = "homePhone";
	public static final String COL_WORKPHONE = "workPhone";
	public static final String COL_MOBILEPHONE = "mobilePhone";
	public static final String COL_EMAIL = "email";
	public static final String COL_ADDRESS = "address";
	public static final String COL_DOB = "dob";
	public static final String COL_IMAGE = "imagePath";
	
	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + 
			"(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_FIRSTNAME + 
			" TEXT NOT NULL DEFAULT ''," + COL_LASTNAME + 
			" TEXT NOT NULL DEFAULT ''," + COL_HOMEPHONE +
			" TEXT NOT NULL DEFAULT ''," + COL_WORKPHONE +
			" TEXT NOT NULL DEFAULT ''," + COL_MOBILEPHONE +
			" TEXT NOT NULL DEFAULT ''," + COL_EMAIL +
			" TEXT NOT NULL DEFAULT ''," + COL_ADDRESS +
			" TEXT NOT NULL DEFAULT ''," + COL_DOB +
			" TEXT NOT NULL DEFAULT ''," + COL_IMAGE
			+ " TEXT NOT NULL DEFAULT '')";
	
	
	private SQLiteDatabase _db;
	
	public static final String[] FIELDS = {COL_ID, COL_FIRSTNAME, COL_LASTNAME,
		COL_HOMEPHONE, COL_WORKPHONE, COL_MOBILEPHONE, COL_ADDRESS, COL_DOB};
	
	public DatabaseHelper(Context context) {
		super(context, TABLE_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}
	
	/**
	 * Edits the values of a contact.
	 */
	public void editContact(long id, String fName, String lName,
			String email, String hPhone, String mPhone, String wPhone,
			String dob, String image, String address) {
		final ContentValues values = new ContentValues();
		values.put(COL_ID, id);
		values.put(COL_FIRSTNAME, fName);
		values.put(COL_LASTNAME, lName);
		values.put(COL_HOMEPHONE, hPhone);
		values.put(COL_WORKPHONE, wPhone);
		values.put(COL_MOBILEPHONE, mPhone);
		values.put(COL_EMAIL, email);
		values.put(COL_ADDRESS, address);
		values.put(COL_DOB, dob);
		values.put(COL_IMAGE, image);
		_db.update(DB_TABLE, values, COL_ID + "=" + id, null);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		_db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
		onCreate(_db);
	}

}
