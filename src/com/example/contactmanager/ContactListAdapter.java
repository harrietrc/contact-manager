package com.example.contactmanager;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class ContactListAdapter extends CursorAdapter {

	private Context context;

	public ContactListAdapter(Context context, Cursor c) {
		super(context, c);
	}

	/**
	 * Gets a row layout and views for a contact in the contact list.
	 */
	public void bindView(View view, Context context, Cursor cursor) {

		// Get the relevant views
		ImageView image = (ImageView) view.findViewById(R.id.contactImage);
		TextView name = (TextView) view.findViewById(R.id.contactName);
		TextView number = (TextView) view.findViewById(R.id.contactNumber);
		
		// Change cursor to contact
		Contact contact = ContactList.cursorToContact(cursor);
		
		System.out.println("Name: " + contact.getFirstName());
		
		// Populate the row with data
		name.setText(contact.getFullName());
		number.setText(contact.getPrimaryNumber());
//		name.setText(cursor.getString(cursor.getColumnIndex(cursor
//				.getColumnName(2))));
//		number.setText(cursor.getString(cursor.getColumnIndex(cursor
//				.getColumnName(3))));
		// image.setImageResource(c.getImageId());
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View listItemView = inflater.inflate(R.layout.list_item, null);
		return listItemView;
	}


}