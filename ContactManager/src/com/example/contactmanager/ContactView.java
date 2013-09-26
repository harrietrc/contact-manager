package com.example.contactmanager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
//import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter for the view contact screen, which I have treated as a list of
 * attributes.
 */
public class ContactView extends Activity {
	
	private Contact _contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		_contact = new Contact(); // Obviously I'll change this.
		//Intent intent = getIntent();				  <-
		//String value = intent.getStringExtra("key") <- useful later
		setContentView(R.layout.contact_view);
		setViews();	
		initialiseActionBar();
	}
	
	/**
	 * Initialises the action bar.
	 */
	private void initialiseActionBar()
	{
		final ActionBar actionBar = getActionBar();
		actionBar.setTitle(_contact.getFullName());
		actionBar.setLogo(R.drawable.back);
		actionBar.setHomeButtonEnabled(true);
		
		// Add a little padding between the 'logo' and title
		//ImageView view = (ImageView)findViewById(android.R.id.home);
		//view.setPadding(0, 0, 50, 0);
	}
	
	/**
	 * Provides different actions that are executed when an actionbar
	 * action is selected.
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_edit:
			Intent toEdit = new Intent(ContactView.this, EditContactView.class);
			startActivity(toEdit);
			return true;
		case R.id.action_delete:
			DeletePrompt dialogue = new DeletePrompt();
			dialogue.show(getFragmentManager(), "dialogue");
			return true;
		case android.R.id.home:
			// Should return the user to the same place in the main list that they were at before.
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * Inflates the action bar and adds items to it.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 *  Set up all the views. Could have set up a special structure as in 
	 * ContactListAdapter, but since they're only required in this method
	 * so I haven't. 
	 * */
	private void setViews() {
		ImageView image = (ImageView) findViewById(R.id.image);
		TextView name = (TextView) findViewById(R.id.fullName);
		TextView mobile = (TextView) findViewById(R.id.mobilePhone);
		TextView home = (TextView) findViewById(R.id.homePhone);
		TextView work = (TextView) findViewById(R.id.workPhone);
		TextView email = (TextView) findViewById(R.id.emailAddress);
		TextView address = (TextView) findViewById(R.id.homeAddress);
		TextView dob = (TextView) findViewById(R.id.dateOfBirth);
		image.setImageResource(_contact.getImageId());
		name.setText(_contact.getFullName());
		mobile.setText(_contact.getMobilePhone());
		home.setText(_contact.getHomePhone());
		work.setText(_contact.getWorkPhone());
		email.setText(_contact.getEmail());
		address.setText(_contact.getAddress());
		dob.setText(_contact.getDOB());
	}
	
}

