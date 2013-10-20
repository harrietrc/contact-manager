package com.example.contactmanager;

import java.util.List;

import android.os.Bundle;
import android.app.ActionBar;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	private ListView _listView;
	private ContactListAdapter _adapter;
	private ContactList _dm;
	private Cursor _cursor;

	/**
	 * Initialises the action bar with a spinner for sorting.
	 */
	private void initialiseActionBar() {
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setLogo(null);

		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Sorting options
		// final String[] spinnerValues =
		// getResources().getStringArray(R.array.sort_options);

		// ArrayAdapter<String> adapter = new
		// ArrayAdapter<String>(actionBar.getThemedContext(),
		// android.R.layout.simple_spinner_item, android.R.id.text1,
		// spinnerValues);

		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Set up the navigation for the spinner
		// actionBar.setListNavigationCallbacks(adapter, this);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Sets the action bar up with a spinner for sorting
		initialiseActionBar();
		
		// Set the data model and open the database
		_dm = new ContactList(this);
		_dm.open();

		// Set up the layout and content
		setContentView(R.layout.activity_main);
		_listView = (ListView) findViewById(android.R.id.list);
		
		// A listener for listening to the user clicking the list items.
		_listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView,
					View clickedView, int clickedViewPosition, long id) {
				Intent toContact = new Intent(clickedView.getContext(),
						ContactView.class);
				
				toContact.putExtra("contactID", id);
				startActivity(toContact);
			}
		});
		
		_cursor = _dm.getAllData();
		_adapter = new ContactListAdapter(this, _cursor);
		_listView.setAdapter(_adapter);
		
		//mock();
		_adapter.getCursor().requery(); // this works, but may be bad design?
	}

	public void mock() {
		_dm.createContact("Jasper", "Doe", "445 123", "446 123",
				"021 123", "4 Domain St.", "my@email.com", "22/10/1993");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Provides different actions that are executed when an actionbar action is
	 * selected.
	 */
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
	
	/**
	 * Data in the ListView is refreshed when the activity is resumed.
	 */
	protected void onResume() {
		_dm.open();
		
		// This is the only method of refreshing that I could get to work. 
		// It seems to be a relatively common method.
		_cursor = _dm.getAllData();
		_adapter.swapCursor(_cursor);
		
		super.onResume();
	}
	
	protected void onPause() {
		_dm.close();
		super.onPause();
	}

}