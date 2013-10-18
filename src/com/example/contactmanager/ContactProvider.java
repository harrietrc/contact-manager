package com.example.contactmanager;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class ContactProvider extends ContentProvider {

	public static final String AUTHORITY = "";
	public static final String SCHEME = "";
	
	public static final String CONTACTS = SCHEME + AUTHORITY + "/contact";
	public static final Uri URI_CONTACTS = Uri.parse(CONTACTS);
	
	public static final String CONTACT_BASE = CONTACTS + "/";
	
	public ContactProvider() {
	}
	
	public int delete(Uri u, String selection, String[] args) {
		// implement to delete
		return 0;
	}
	
	public Uri insert(Uri u, ContentValues values) {
		// for inserting a new row
		throw new UnsupportedOperationException("Not implemented");
	}
	
	public boolean onCreate() {
		return true; // necessary?
	}
	
	public Cursor query(Uri u, String[] projection, String selection, String[] args,
			String sortOrder) {
		Cursor result = null;
		if (URI_CONTACTS.equals(u)) {
			result = ContactsDatabaseHelper.getInstance(getContext()).getReadableDatabase()
					.query(Contact.TABLE_NAME, Contact.FIELDS, null, null, null, null,
							null, null);
			result.setNotificationUri(getContext().getContentResolver(), URI_CONTACTS);
		} else if (u.toString().startsWith(CONTACT_BASE)) {
			final long id = Long.parseLong(u.getLastPathSegment());
			result = ContactsDatabaseHelper.getInstance(getContext()).getReadableDatabase()
					.query(Contact.TABLE_NAME, Contact.FIELDS, Contact.COL_ID + " IS ?", 
							new String[]{String.valueOf(id)}, null, null, null, null);
			result.setNotificationUri(getContext().getContentResolver(), URI_CONTACTS);
		} else {
			// who knows.
		}
		return result;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}
}
