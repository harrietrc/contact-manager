package com.example.contactmanager;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private ListView _listView;
	private ContactList _list;
	private ContactListAdapter _adapter;
	
	/*
	 * Note on action bar: I may change this to actionbarsherlock as currently it's a little inelegant.
	 */
	
	/**
	 * Initialises the action bar with a spinner for sorting.
	 */
	private void initialiseActionBar()
	{
		/*
		 * I don't know whether you look at our lo-fis when marking this, but the reason the layout here
		 * is different than in my prototypes is because I got it implemented as I had planned, but
		 * preferred the button layout as I've left it.
		 */
		
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setLogo(null);
		
	    //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	    
	    // Sorting options
	   // final String[] spinnerValues = getResources().getStringArray(R.array.sort_options);
	    
	    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(actionBar.getThemedContext(),
	    		//android.R.layout.simple_spinner_item, android.R.id.text1, spinnerValues);
	    
	    //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
	    // Set up the navigation for the spinner
	    //actionBar.setListNavigationCallbacks(adapter, this);
	    
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Sets the action bar up with a spinner for sorting
		initialiseActionBar();
		
		// Set up the layout and content
		setContentView(R.layout.activity_main);
		_list = ContactList.getContactList();
		_listView = (ListView) findViewById(R.id.main_listview);
		
		// A listener for listening to the user clicking the list items.
		_listView.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition,
					long id) {
				Intent toContact = new Intent(clickedView.getContext(), ContactView.class);
				startActivity(toContact);
				/* Currently each list row isn't associated with a contact. This is still to be implemented. */
			}
		});
		
		_list.mockList(); /* TEMPORARY */
		
		_adapter = new ContactListAdapter(MainActivity.this, R.layout.activity_main, ContactList._contacts);
		_listView.setAdapter(_adapter);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * Provides different actions that are executed when an actionbar
	 * action is selected.
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search:
			// filler
			return true;
		case R.id.action_add:
			return true;
		case R.id.action_sort:
			// do stuff
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
}
