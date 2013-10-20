package com.example.contactmanager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
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
	private long _id;
	
	/* Views */
	
	private ImageView _image;
	private TextView _name;
	private TextView _mobile;
	private TextView _home;
	private TextView _work;
	private TextView _email;
	private TextView _address;
	private TextView _dob;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set up layout
		setContentView(R.layout.contact_view);

		// Get ID of contact
		Intent intent = getIntent();
		_id = intent.getLongExtra("contactID", 0);
		
		// Set the contact
		_contact = ContactList.getContactByID(_id);
		
		// Set up the views
		initialiseViews();
		setViews();
		
		// Set up the action bar
		initialiseActionBar();
	}
	

	
	protected void onResume() {
		_contact = ContactList.getContactByID(_id);
		setViews();
		super.onResume();
	}

	/**
	 * Initialises the action bar.
	 */
	private void initialiseActionBar() {
		final ActionBar actionBar = getActionBar();
		actionBar.setTitle(_contact.getFullName());
		actionBar.setLogo(R.drawable.back);
		actionBar.setHomeButtonEnabled(true);
		ImageView view = (ImageView) findViewById(android.R.id.home);
		view.setPadding(0, 0, 40, 0);
	}

	/**
	 * Provides different actions that are executed when an actionbar action is
	 * selected.
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		final ContactList ls = new ContactList(this);
		switch (item.getItemId()) {
		case R.id.action_edit:
			Intent toEdit = new Intent(ContactView.this, EditContactView.class);
			toEdit.putExtra("contactID", _id);
			startActivity(toEdit);
			return true;
		case R.id.action_delete:
			class DeletePrompt extends DialogFragment {

				protected DeletePrompt() {
					super();
				}

				@Override
				public Dialog onCreateDialog(Bundle savedInstanceState) {
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setMessage(R.string.delete_prompt)
						.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								ls.deleteContact(_contact);
								getActivity().finish();					
							}
						}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// Can I just leave this blank?
							}
						});
					return builder.create();
				}
			};
			DeletePrompt dialogue = new DeletePrompt();
			dialogue.show(getFragmentManager(), "dialogue");
			return true;
		case android.R.id.home:
			// Should return the user to the same place in the main list that
			// they were at before.
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
	 * Sets the text in each of the text and image views.
	 */
	private void setViews() {
		_image.setImageResource(_contact.getImageId());
		_name.setText(_contact.getFullName());
		_mobile.setText(_contact.getMobilePhone());
		_home.setText(_contact.getHomePhone());
		_work.setText(_contact.getWorkPhone());
		_email.setText(_contact.getEmail());
		_address.setText(_contact.getAddress());
		_dob.setText(_contact.getDOB());
	}
	
	/**
	 * Sets up the views, assigning them to fields.
	 */
	private void initialiseViews() {
		_image = (ImageView) findViewById(R.id.image);
		_name = (TextView) findViewById(R.id.fullName);
		_mobile = (TextView) findViewById(R.id.mobilePhone);
		_home = (TextView) findViewById(R.id.homePhone);
		_work = (TextView) findViewById(R.id.workPhone);
		_email = (TextView) findViewById(R.id.emailAddress);
		_address = (TextView) findViewById(R.id.homeAddress);
		_dob = (TextView) findViewById(R.id.dateOfBirth);
	}

}
