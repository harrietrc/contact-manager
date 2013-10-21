package com.example.contactmanager;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class MainActivity extends ListActivity implements
		ActionBar.OnNavigationListener {

	private ListView _listView;
	private ContactListAdapter _adapter;
	private ContactList _dm;
	private Cursor _cursor;
	private String _sortOrder;

	// String giving the default column to sort by.
	public static final String DEFAULT_SORT_ORDER = "ORDER BY "
			+ DatabaseHelper.COL_FIRSTNAME + " ASC";

	/**
	 * Initialises the action bar with a spinner for sorting.
	 */
	private void initialiseActionBar() {
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setLogo(null);

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
		actionBar.setLogo(R.drawable.back);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
			String query = intent.getStringExtra(SearchManager.QUERY);

			Cursor cursor = _dm.getMatch(query);

			ContactListAdapter adapter = new ContactListAdapter(this, cursor);
			_listView.setAdapter(adapter);

			actionBarSearchView();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));

		return super.onCreateOptionsMenu(menu);
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
			extra.putLong("contactID", id);
			extra.putString("activity", "add");
			Intent toContact = new Intent(this, EditContactView.class);
			toContact.putExtras(extra);
			startActivity(toContact);
			return true;
		case android.R.id.home:
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

		// This is the only method of refreshing that I could get to work.
		// It seems to be a relatively common method.
		_cursor = _dm.getAllData(_sortOrder);
		_adapter.swapCursor(_cursor);

		super.onResume();
	}

	protected void onPause() {
		_dm.close();
		super.onPause();
	}

	/**
	 * Should return true if handled, false otherwise.
	 */
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		if (itemPosition == 0) {
			_sortOrder = "ORDER BY " + DatabaseHelper.COL_FIRSTNAME + " ASC";
			_cursor = _dm.getAllData(_sortOrder);
			_adapter.swapCursor(_cursor);
			return true;
		} else if (itemPosition == 1) {
			_sortOrder = "ORDER BY " + DatabaseHelper.COL_LASTNAME + " ASC";
			_cursor = _dm.getAllData(_sortOrder);
			_adapter.swapCursor(_cursor);
			return true;
		} else if (itemPosition == 2) {
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