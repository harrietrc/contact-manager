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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Adapter for the view contact screen, which I have treated as a list of
 * attributes.
 */
public class ContactView extends Activity {

	private Contact _contact;
	private long _id;
	private ContactList _ls;

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

		// No more annoying transition animation
		overridePendingTransition(0, 0);

		// Set up layout
		setContentView(R.layout.contact_view);

		// Get ID of contact
		Intent intent = getIntent();
		_id = intent.getLongExtra("contactID", 0);

		// If the ID is 0, the contact was blank and has been deleted. Return to
		// the list view.
		if (_id == -1) {
			System.out.println("yes");
			finish();
			return; // Else onCreate() keeps executing
		}

		// Set the contact
		_ls = new ContactList(this);
		_contact = _ls.getContactByID(_id);

		// Set up the views
		initialiseViews();
		setViews();

		// Set up the action bar
		initialiseActionBar();

	}

	/**
	 * Due to my decision to call finish() when starting EditContactView, I
	 * don't believe this is ever called. I've left the implementation anyway
	 * because this is (more or less) the expected behaviour.
	 */
	protected void onResume() {

		// Gets rid of transition animation
		overridePendingTransition(0, 0);

		// Set contact by ID
		_contact = _ls.getContactByID(_id);

		// Set up layout and variables
		setViews();
		super.onResume();
	}

	/**
	 * Initialises the action bar, setting icons and titles.
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
			Bundle extra = new Bundle();
			extra.putLong("contactID", _id);
			extra.putString("activity", "edit");
			toEdit.putExtras(extra);
			startActivity(toEdit);
			/*
			 * finish() is called so that onCreate() is able to handle intents
			 * should a contact need to be removed due to having blank fields.
			 */
			finish();
			return true;
		case R.id.action_delete:

			/*
			 * A delete prompt that is instantiated if the user selects the
			 * delete action.
			 */
			class DeletePrompt extends DialogFragment {

				protected DeletePrompt() {
					super();
				}

				@Override
				public Dialog onCreateDialog(Bundle savedInstanceState) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setMessage(R.string.delete_prompt)
							// Delete the contact
							.setPositiveButton(R.string.yes,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											ls.deleteContact(_contact);
											getActivity().finish();
										}
									})
							// Cancel deletion action
							.setNegativeButton(R.string.no,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									});
					return builder.create();
				}
			}
			;
			DeletePrompt dialogue = new DeletePrompt();
			dialogue.show(getFragmentManager(), "dialogue");
			return true;
		case android.R.id.home:
			// The contacts list should still be open. Finishing the activity
			// will return the user to their place in the list.
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
		String mobile = _contact.getMobilePhone();
		String home = _contact.getHomePhone();
		String work = _contact.getWorkPhone();
		String email = _contact.getEmail();
		String address = _contact.getAddress();
		String dob = _contact.getDOB();

		// Set view data for views that will always take up space, regardless of
		// whether they're blank or not.
		_image.setImageBitmap(_contact.getImage());
		_name.setText(_contact.getFullName());

		/*
		 * Go through the contact's attributes and hide headings for blank ones.
		 * If the attribute is not an empty string, fill the relevant view with
		 * data.
		 */

		String[] attributes = { mobile, home, work, email, address, dob };
		LinearLayout[] headings = {
				(LinearLayout) findViewById(R.id.mobile),
				(LinearLayout) findViewById(R.id.home),
				(LinearLayout) findViewById(R.id.work),
				(LinearLayout) findViewById(R.id.email),
				(LinearLayout) findViewById(R.id.address),
				(LinearLayout) findViewById(R.id.dob) };
		TextView[] text = { _mobile, _home, _work, _email, _address, _dob };

		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i].trim().equals("")) {
				headings[i].setVisibility(View.GONE);
				//text[i].setVisibility(View.GONE);
			} else {
				text[i].setText(attributes[i]);
			}
		}
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
