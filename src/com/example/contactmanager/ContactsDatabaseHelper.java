package com.example.contactmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactsDatabaseHelper extends SQLiteOpenHelper {

	// DatabaseHandler should only really exist once
	public static ContactsDatabaseHelper singleton;
	
	private static final String DATABASE_NAME = "contactsDatabase"; 
	private final Context context;
	
	public static ContactsDatabaseHelper getInstance(final Context context) {
		if (singleton == null) {
			singleton = new ContactsDatabaseHelper(context);
		}
		return singleton;
	}
	
	public ContactsDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
		// Not sure whether this is necessary for Android. Keeps context when window is closed.
		this.context = context.getApplicationContext();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Unnecessary for now?
	}
	

}
