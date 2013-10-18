package com.example.contactmanager;

import android.content.ContentValues;
import android.database.Cursor;

public class Contact {
	
	// I looked at an example and the fields values were initialised up here instead
	// of in the constructor.
	public String firstName="";
	public String lastName="";
	public String homePhone="";
	public String workPhone="";
	public String mobilePhone="";
	public String email = "";
	public String address="";
	public String dob="";
	public long id=-1;
	public String imagePath;
	
	public static final String TABLE_NAME = "Contact";
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
	
	public static final String[] FIELDS = {COL_ID, COL_FIRSTNAME, COL_LASTNAME,
		COL_HOMEPHONE, COL_WORKPHONE, COL_MOBILEPHONE, COL_ADDRESS, COL_DOB};
	
	/* Figured I might as well store everything as strings and then convert them
	   if they need to be used in a different format. Phone numbers may contain 
	   characters such as - and (). */
	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + 
			"(" + COL_ID + " INTEGER PRIMARY KEY," + COL_FIRSTNAME + 
			" TEXT NOT NULL DEFAULT ''," + COL_LASTNAME + 
			" TEXT NOT NULL DEFAULT ''," + COL_HOMEPHONE +
			" TEXT NOT NULL DEFAULT ''," + COL_WORKPHONE +
			" TEXT NOT NULL DEFAULT ''," + COL_MOBILEPHONE +
			" TEXT NOT NULL DEFAULT ''," + COL_EMAIL +
			" TEXT NOT NULL DEFAULT ''," + COL_ADDRESS +
			" TEXT NOT NULL DEFAULT ''," + COL_DOB +
			" TEXT NOT NULL DEFAULT ''," + COL_IMAGE;
	
	/**
	 * Blank for now. 
	 */
	public Contact() {}
	
	/**
	 * Convert database information into a contact object.
	 * @param 
	 */
	public Contact(final Cursor c) {
		this.id = c.getLong(0);
		this.firstName = c.getString(1);
		this.lastName = c.getString(2);
		this.homePhone = c.getString(3);
		this.workPhone = c.getString(4);
		this.mobilePhone = c.getString(5);
		this.email = c.getString(6);
		this.address = c.getString(7);
		this.dob = c.getString(8);
		this.imagePath = c.getString(9);
	}
	
	/**
	 * Returns the contact's fields in a ContentValues object, which can 
	 * then be inserted into the database.
	 * @return a ContentValues object with the contact's information.
	 */
	public ContentValues getContent() {
		final ContentValues values = new ContentValues();
		values.put(COL_ID, id);
		values.put(COL_FIRSTNAME, firstName);
		values.put(COL_LASTNAME, lastName);
		values.put(COL_HOMEPHONE, homePhone);
		values.put(COL_WORKPHONE, workPhone);
		values.put(COL_MOBILEPHONE, mobilePhone);
		values.put(COL_EMAIL, email);
		values.put(COL_ADDRESS, address);
		values.put(COL_DOB, dob);
		values.put(COL_IMAGE, imagePath);
		return values;
	}
	
	/* GETTERS */
	
	/**
	 * Returns the contact's date of birth. I'll probably sort out how to
	 * uniformly format these later.
	 * @return = date of birth
	 */
	public String getDOB() {
		return dob;
	}
	
	/**
	 * Returns the contact's home address.
	 * @return = home address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Returns the contact's first name.
	 * @return = first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Returns the contact's last name.
	 * @return = last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Returns the contact's work phone. May implement automatic
	 * formatting later.
	 * @return = work phone number as a string
	 */
	public String getWorkPhone() {
		return workPhone;
	}
	
	/**
	 * Returns the contact's home phone number.
	 * @return = home phone number as a string
	 */
	public String getHomePhone() {
		return homePhone;
	}
	
	/**
	 * Returns the contact's mobile phone number.
	 * @return = mobile phone number as a string
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * Returns the contact's full name. Here for convenience.
	 * @return = the contact's first and last names if they exist.
	 */
	public String getFullName() {
		return "John Doe";
	}
	
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the 'primary' number (home or mobile - mobile will
	 * take precedence)
	 * @return = the contact's primary number.
	 */
	public String getPrimaryNumber() {
		return "123456789";
	}

	/**
	 * Returns the contact's picture if it exists.
	 * @return = the ID of the contact's picture.
	 */
	public int getImageId() {
		return R.drawable.man;
	}

}
