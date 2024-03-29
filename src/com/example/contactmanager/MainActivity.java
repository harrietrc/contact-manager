package com.example.contactmanager;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

public class MainActivity extends ListActivity implements
		ActionBar.OnNavigationListener {

	private ListView _listView; // The main component of the activity
	private ContactListAdapter _adapter; // Adapts the data model
	private ContactList _dm; // The data model for this view
	private Cursor _cursor; // A cursor with all the contacts and data for the listview
	private String _sortOrder; // A string to control the SQLite search
	private Menu _menu; // The menu / action bar for the activity
	private boolean _isMenuItemsStateHide; // If true, the add and search buttons should be hidden
	private String _query; // The current search query (if relevant)

	// String giving the default column to sort by.
	public static final String DEFAULT_SORT_ORDER = "ORDER BY "
			+ DatabaseHelper.COL_FIRSTNAME + " ASC";

	/**
	 * Initialises the action bar with a spinner for sorting.
	 */
	private void initialiseActionBar() {
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		
		setTitle(this.getString(R.string.all_contacts));

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Sorting options
		final String[] spinnerValues = getResources().getStringArray(
				R.array.sort_options);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				actionBar.getThemedContext(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				spinnerValues);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Set up the navigation for the spinner
		actionBar.setListNavigationCallbacks(adapter, this);
	}

	/**
	 * Sets up the action bar for the search results view of the activity.
	 */
	private void actionBarSearchView() {
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setTitle(this.getString(R.string.search_title));
		actionBar.setDisplayShowTitleEnabled(true);
		ImageView view = (ImageView) findViewById(android.R.id.home);
		view.setPadding(0, 0, 40, 0);
		_isMenuItemsStateHide = true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Make sure the menu items aren't hidden
		_isMenuItemsStateHide = false;

		// Removes transition animation
		overridePendingTransition(0, 0);

		_sortOrder = DEFAULT_SORT_ORDER;

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

		_cursor = _dm.getAllData(_sortOrder);
		_adapter = new ContactListAdapter(this, _cursor);
		_listView.setAdapter(_adapter);

		_adapter.getCursor().requery();

		// Deal with the ACTION_SEARCH intent for the search function in the
		// action bar
		handleIntent(getIntent());
	}

	/**
	 * Handles the intent for this activity. For the action bar search.
	 * 
	 * @param intent
	 */
	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			_query = intent.getStringExtra(SearchManager.QUERY);
			
			// Perform the search
			boolean isNotEmptyResults = performSearchQuery();
			
			if (isNotEmptyResults) {
				// Customise the action bar for search
				actionBarSearchView();
			} else {
				// Let the user know that the result of their search was empty.
				new AlertDialog.Builder(this)
					.setTitle(this.getString(R.string.no_results_prompt))
					.setNeutralButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
						
						/**
						 * Close the dialogue box.
						 */
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
						}
					})
					.show(); // Show the dialogue box.
			}
		}
	}
	
	/**
	 * Perform a search, based on an existing query. A class-wide cursor will
	 * be set.
	 * 
	 * @return = true if the query matched some results, false otherwise
	 */
	private boolean performSearchQuery() {
		Cursor cursor = _dm.getMatch(_query);
		if (cursor.getCount() != 0) { // Empty cursors should not result in the search results view.
			ContactListAdapter adapter = new ContactListAdapter(this, _cursor);
			_listView.setAdapter(adapter);
			_cursor = cursor;
			return true;
		} 
		return false;
	}

	/**
	 * Sets up the action bar, inflating and initialising actions.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Save the reference for later
		_menu = menu;
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));

		boolean ret = super.onCreateOptionsMenu(menu);
		
		// Set the add and search buttons to visible, in case they were 
		// turned off for searching.
		if (_isMenuItemsStateHide == false) {
			_menu.findItem(R.id.action_add).setVisible(true);
			_menu.findItem(R.id.search).setVisible(true);
		} else { // Hide the menu items (search results are active)
			_menu.findItem(R.id.action_add).setVisible(false);
			_menu.findItem(R.id.search).setVisible(false);
			// Get rid of the spinner
			getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		}
		
		return ret;
	}

	/**
	 * Provides different actions that are executed when an actionbar action is
	 * selected.
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			Contact contact = _dm.newContact();
			long id = contact.getId();
			Bundle extra = new Bundle();
			
			// Provide the contact ID and the type of activity (add) to the next activity
			extra.putLong("contactID", id);
			extra.putString("activity", "add");
			
			Intent toContact = new Intent(this, EditContactView.class);
			toContact.putExtras(extra);
			startActivity(toContact);
			return true;
		case android.R.id.home: // I.e. back
			initialiseActionBar();
			finish();
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Data in the ListView is refreshed when the activity is resumed.
	 */
	protected void onResume() {
		// No more transition animation
		overridePendingTransition(0, 0);

		_dm.open();

		if (_isMenuItemsStateHide == false) { // We are in the usual list view
			// This is the only method of refreshing that I could get to work.
			// It seems to be a relatively common method.
			_cursor = _dm.getAllData(_sortOrder);
			_adapter.swapCursor(_cursor);
		} else { // Search results are shown
			boolean isNotEmptyResults = performSearchQuery();
			if (isNotEmptyResults) {
				performSearchQuery();
				_adapter.swapCursor(_cursor);
			} else {
				// If the results list is empty due to an update to a contact, 
				// return to the main list.
				_cursor = _dm.getAllData(_sortOrder);
				_adapter.swapCursor(_cursor);
				finish();
			}
		}

		super.onResume();
	}

	/**
	 * On pause... pause.
	 */
	protected void onPause() {
		super.onPause();
	}

	/**
	 * Should return true if handled, false otherwise.
	 */
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		if (itemPosition == 0) { // Sort by first name
			_sortOrder = "ORDER BY " + DatabaseHelper.COL_FIRSTNAME + " ASC";
			_cursor = _dm.getAllData(_sortOrder);
			_adapter.swapCursor(_cursor);
			return true;
		} else if (itemPosition == 1) { // Sort by last name
			_sortOrder = "ORDER BY " + DatabaseHelper.COL_LASTNAME + " ASC";
			_cursor = _dm.getAllData(_sortOrder);
			_adapter.swapCursor(_cursor);
			return true;
		} else if (itemPosition == 2) { // Sort by number
			_sortOrder = "ORDER BY CASE WHEN LENGTH(mobilePhone) > 0 THEN "
					+ DatabaseHelper.COL_MOBILEPHONE
					+ " WHEN LENGTH(homePhone) > 0 THEN "
					+ DatabaseHelper.COL_HOMEPHONE + " ELSE "
					+ DatabaseHelper.COL_WORKPHONE + " END";
			_cursor = _dm.getAllData(_sortOrder);
			_adapter.swapCursor(_cursor);
			return true;
		} else {
			return false;
		}
	}

}