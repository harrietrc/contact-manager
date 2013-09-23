package com.example.contactmanager;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class ContactListAdapter extends ArrayAdapter<Contact> {
	
	private LayoutInflater _inflater;
	private List<Contact> _contacts;
	private Context _context;
	private ViewStructure _views;
	
	public ContactListAdapter(Context context, int textViewResourceId, ArrayList<Contact> contacts) {
		super(context, textViewResourceId, contacts);
		_context = context;
		_inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		_contacts = contacts;
	}
	
	private static class ViewStructure {
		ImageView image;
		TextView name;
		TextView number;
	}
	
	public View getView(int position, View row, ViewGroup parent) {
		
		// The contact that we're working with
		Contact c = (Contact) _contacts.get(position);
		
		// If the view is null, inflate it so that we can render/see it.
		if (row == null) {
			row = _inflater.inflate(R.layout.list_item, null);
			_views = new ViewStructure();
			// Get the relevant views
			if (c.getImageId() != 0) { // I.e. the image field has not been initialised
				_views.image = (ImageView) row.findViewById(R.id.contactImage);
				_views.name = (TextView) row.findViewById(R.id.contactName);
				_views.number = (TextView) row.findViewById(R.id.contactNumber);
			} else {
				
			}
			row.setTag(_views);
		} else { // Don't need to inflate it
			_views = (ViewStructure) row.getTag();
		}
	
		// Populate the row with data
		_views.name.setText(c.getFullName());
		_views.number.setText(c.getPrimaryNumber());
		_views.image.setImageResource(c.getImageId());
		
		return row;
	}

}