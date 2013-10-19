package com.example.contactmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_NAME = "Contact";
	public static final String DB_NAME = "ContactDatabase";
	public static final String DB_TABLE = "cTable";
	public static final int TABLE_VERSION = 1;
	public static final String COL_ID = "id";
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
	
	//private final Context _context;
	//private ContactDatabaseHelper _helper;
	private SQLiteDatabase _db;
	
	public static final String[] FIELDS = {COL_ID, COL_FIRSTNAME, COL_LASTNAME,
		COL_HOMEPHONE, COL_WORKPHONE, COL_MOBILEPHONE, COL_ADDRESS, COL_DOB};
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
		ContentValues values = new ContentValues();
	}
	
//	private static class ContactDatabaseHelper extends SQLiteOpenHelper {
//	
//		public ContactDatabaseHelper(Context context) {
//			super(context, TABLE_NAME, null, TABLE_VERSION);
//		}
//
//		/* Figured I might as well store everything as strings and then convert them
//		   if they need to be used in a different format. Phone numbers may contain 
//		   characters such as - and (). */
//		public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + 
//				"(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COL_FIRSTNAME + 
//				" TEXT NOT NULL DEFAULT ''," + COL_LASTNAME + 
//				" TEXT NOT NULL DEFAULT ''," + COL_HOMEPHONE +
//				" TEXT NOT NULL DEFAULT ''," + COL_WORKPHONE +
//				" TEXT NOT NULL DEFAULT ''," + COL_MOBILEPHONE +
//				" TEXT NOT NULL DEFAULT ''," + COL_EMAIL +
//				" TEXT NOT NULL DEFAULT ''," + COL_ADDRESS +
//				" TEXT NOT NULL DEFAULT ''," + COL_DOB +
//				" TEXT NOT NULL DEFAULT ''," + COL_IMAGE;
//
//		@Override
//		public void onCreate(SQLiteDatabase db) {
//			db.execSQL(CREATE_TABLE);
//		}
//
//		/**
//		 * Drop the existing table and make a new one
//		 */
//		@Override
//		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//			db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
//			onCreate(db);
//		}
//	
//	}
	
	public ContactHelper(Context c) {
		super(c, DB_NAME, null, 1);
		//_context = c;
	}
	
//	/**
//	 * Open the database.
//	 * @return The open database class
//	 * @throws SQLException
//	 */
//	public ContactHelper open() throws SQLException {
//		_helper = new ContactDatabaseHelper(_context);
//		_db = _helper.getWritableDatabase();
//		return this;
//	}
	
//	/**
//	 * Close the database.
//	 */
//	public void close() {
//		_helper.close();
//	}
	
	/**
	 * Extracts data from the database.
	 * @return
	 */
	public String getContent() {
		String[] cols = new String[] {
				COL_ID, COL_FIRSTNAME, COL_LASTNAME, COL_HOMEPHONE,
				COL_WORKPHONE, COL_MOBILEPHONE, COL_EMAIL, COL_ADDRESS,
				COL_DOB, COL_IMAGE
		};
		
		Cursor c = _db.query(DB_TABLE, cols, null, null, null, null, null, null);
		String result = "";
		
		int iID = c.getColumnIndex(COL_ID);
		int iFName = c.getColumnIndex(COL_FIRSTNAME);
		int iLName = c.getColumnIndex(COL_LASTNAME);
		int iHPhone = c.getColumnIndex(COL_HOMEPHONE);
		int iWPhone = c.getColumnIndex(COL_WORKPHONE);
		int iMPhone = c.getColumnIndex(COL_MOBILEPHONE);
		int iEmail = c.getColumnIndex(COL_EMAIL);
		int iAddress = c.getColumnIndex(COL_ADDRESS);
		int iDOB = c.getColumnIndex(COL_DOB);
		int iImage = c.getColumnIndex(COL_IMAGE);
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iID) + " " + c.getString(iFName)
					+ " " + c.getString(iLName) + " " + c.getString(iHPhone)
					+ " " + c.getString(iWPhone) + " " + c.getString(iMPhone)
					+ " " + c.getString(iEmail) + " " + c.getString(iAddress)
					+ " " + c.getString(iDOB) + " " + c.getString(iImage);
		}
		
		return result;
	}
	
	/**
	 * Edits the values of a contact.
	 */
	public void editContent(long id, String fName, String lName,
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
	
	/**
	 * Inserts a new contact into the database. Different from editContent in
	 * that it doesn't require an ID as a parameter.
	 */
	public long newContent(String fName, String lName,
			String email, String hPhone, String mPhone, String wPhone,
			String dob, String image, String address) {
		ContentValues values = new ContentValues();
		values.put(COL_FIRSTNAME, fName);
		values.put(COL_LASTNAME, lName);
		values.put(COL_HOMEPHONE, hPhone);
		values.put(COL_WORKPHONE, wPhone);
		values.put(COL_MOBILEPHONE, mPhone);
		values.put(COL_EMAIL, email);
		values.put(COL_ADDRESS, address);
		values.put(COL_DOB, dob);
		values.put(COL_IMAGE, image);
		
		return _db.insert(DB_TABLE, null, values);
	}
	
	/**
	 * Deletes a contact from the database.
	 * @param row = the row in the database to be deleted.
	 */
	public void deleteContact(long id) {
		_db.delete(DB_TABLE,  COL_ID + "=" + id, null);
	}
	
	// Accessors
	
	/**
     * Returns the contact's date of birth. I'll probably sort out how to
     * uniformly format these later.
     * @return = date of birth
     */
    public String getDOB(int i) {
    	String[] cols = new String[]{COL_ID, COL_FIRSTNAME, COL_LASTNAME, COL_HOMEPHONE,
				COL_WORKPHONE, COL_MOBILEPHONE, COL_EMAIL, COL_ADDRESS,
				COL_DOB, COL_IMAGE};
    	Cursor c = _db.query(DB_TABLE, cols, COL_ID + "=" + i, null, null, null, null);
    	if (c != null) {
    		c.moveToFirst();
    		String dob = c.getString(8);
    		return dob;
    	}
    	return null;
    }
    
    /**
     * Returns the contact's home address.
     * @return = home address
     */
    public String getAddress(int i) {
    	String[] cols = new String[]{COL_ID, COL_FIRSTNAME, COL_LASTNAME, COL_HOMEPHONE,
				COL_WORKPHONE, COL_MOBILEPHONE, COL_EMAIL, COL_ADDRESS,
				COL_DOB, COL_IMAGE};
    	Cursor c = _db.query(DB_TABLE, cols, COL_ID + "=" + i, null, null, null, null);
    	if (c != null) {
    		c.moveToFirst();
    		String address = c.getString(7);
    		return address;
    	}
    	return null;
    }
    
    /**
     * Returns the contact's first name.
     * @return = first name
     */
    public String getFirstName(int i) {
    	String[] cols = new String[]{COL_ID, COL_FIRSTNAME, COL_LASTNAME, COL_HOMEPHONE,
				COL_WORKPHONE, COL_MOBILEPHONE, COL_EMAIL, COL_ADDRESS,
				COL_DOB, COL_IMAGE};
    	Cursor c = _db.query(DB_TABLE, cols, COL_ID + "=" + i, null, null, null, null);
    	if (c != null) {
    		c.moveToFirst();
    		String fName = c.getString(1);
    		return fName;
    	}
    	return null;
    }
    
    /**
     * Returns the contact's last name.
     * @return = last name
     */
    public String getLastName(int i) {
    	String[] cols = new String[]{COL_ID, COL_FIRSTNAME, COL_LASTNAME, COL_HOMEPHONE,
				COL_WORKPHONE, COL_MOBILEPHONE, COL_EMAIL, COL_ADDRESS,
				COL_DOB, COL_IMAGE};
    	Cursor c = _db.query(DB_TABLE, cols, COL_ID + "=" + i, null, null, null, null);
    	if (c != null) {
    		c.moveToFirst();
    		String lName = c.getString(2);
    		return lName;
    	}
    	return null;
    }
    
    /**
     * Returns the contact's work phone. May implement automatic
     * formatting later.
     * @return = work phone number as a string
     */
    public String getWorkPhone(int i) {
    	String[] cols = new String[]{COL_ID, COL_FIRSTNAME, COL_LASTNAME, COL_HOMEPHONE,
				COL_WORKPHONE, COL_MOBILEPHONE, COL_EMAIL, COL_ADDRESS,
				COL_DOB, COL_IMAGE};
    	Cursor c = _db.query(DB_TABLE, cols, COL_ID + "=" + i, null, null, null, null);
    	if (c != null) {
    		c.moveToFirst();
    		String wPhone = c.getString(4);
    		return wPhone;
    	}
    	return null;
    }
    
    /**
     * Returns the contact's home phone number.
     * @return = home phone number as a string
     */
    public String getHomePhone(int i) {
    	String[] cols = new String[]{COL_ID, COL_FIRSTNAME, COL_LASTNAME, COL_HOMEPHONE,
				COL_WORKPHONE, COL_MOBILEPHONE, COL_EMAIL, COL_ADDRESS,
				COL_DOB, COL_IMAGE};
    	Cursor c = _db.query(DB_TABLE, cols, COL_ID + "=" + i, null, null, null, null);
    	if (c != null) {
    		c.moveToFirst();
    		String hPhone = c.getString(3);
    		return hPhone;
    	}
    	return null;
    }
    
    /**
     * Returns the contact's mobile phone number.
     * @return = mobile phone number as a string
     */
    public String getMobilePhone(int i) {
    	String[] cols = new String[]{COL_ID, COL_FIRSTNAME, COL_LASTNAME, COL_HOMEPHONE,
				COL_WORKPHONE, COL_MOBILEPHONE, COL_EMAIL, COL_ADDRESS,
				COL_DOB, COL_IMAGE};
    	Cursor c = _db.query(DB_TABLE, cols, COL_ID + "=" + i, null, null, null, null);
    	if (c != null) {
    		c.moveToFirst();
    		String mPhone = c.getString(5);
    		return mPhone;
    	}
    	return null;
    }

    /**
     * Returns the contact's full name. Here for convenience.
     * @return = the contact's first and last names if they exist.
     */
    public String getFullName(int i) {
    	return getFirstName(i) + getLastName(i);
    }
    
    public String getEmail(int i) {
    	String[] cols = new String[]{COL_ID, COL_FIRSTNAME, COL_LASTNAME, COL_HOMEPHONE,
				COL_WORKPHONE, COL_MOBILEPHONE, COL_EMAIL, COL_ADDRESS,
				COL_DOB, COL_IMAGE};
    	Cursor c = _db.query(DB_TABLE, cols, COL_ID + "=" + i, null, null, null, null);
    	if (c != null) {
    		c.moveToFirst();
    		String email = c.getString(6);
    		return email;
    	}
    	return null;
    }

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		_db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
		onCreate(_db);
	}

}
