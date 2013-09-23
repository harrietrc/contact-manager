package com.example.contactmanager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private ListView _listView;
	private ContactList _list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_list = ContactList.getContactList();
		_listView = (ListView) findViewById(R.id.main_listview);
		
		_list.mockList();
		
		ContactListAdapter adapter = new ContactListAdapter(MainActivity.this, R.layout.activity_main, ContactList._contacts);
		_listView.setAdapter(adapter);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// Action bar buttons actions
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search:
			// filler
			return true;
		case R.id.action_add:
			// do stuff
			return true;
		case R.id.action_sort:
			// do stuff
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
}
