package com.example.contactmanager;

import java.io.ByteArrayOutputStream;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditContactView extends Activity {
	
	private Contact _contact;
	private InputMethodManager _imm;
	private long _id;
	private String _activity;  // either 'add' or 'edit'
	private static final int CAMERA_REQUEST = 1888;
	private byte[] _image = null;
	
	private ImageView image;
	private EditText first;
	private EditText last;
	private EditText mobile;
	private EditText home;
	private EditText work;
	private EditText email;
	private EditText address;
	private EditText dob;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_contact);
		
		overridePendingTransition(0,0);
		
		// Sets up the keyboard
		_imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
		
		/* Make this a method */
		
		// Set up database
		DatabaseHelper dbh = new DatabaseHelper(this);
		SQLiteDatabase db = dbh.getWritableDatabase();
		
		// Get the contact
		Intent intent = getIntent();
		Bundle extra = intent.getExtras();
		_id = extra.getLong("contactID", 0);
		Cursor cursor = db
				.rawQuery(
						"SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE _id = " +
				_id, null);
		cursor.moveToFirst();
		_contact = ContactList.cursorToContact(cursor);
		
		// Set up the views and action bar
		_activity = extra.getString("activity", "edit");
		setFields();
		initialiseActionBar();
	}
	
	/**
	 * Initialises the action bar.
	 */
	private void initialiseActionBar()
	{
		final ActionBar actionBar = getActionBar();
		
		// Allows differentiation between edit and add contact screens
		if (_activity.equals("edit")) {
			actionBar.setTitle("Editing "+ _contact.getFullName());
		} else {
			actionBar.setTitle("New Contact");
		}
		
		actionBar.setLogo(R.drawable.cancel);
		actionBar.setHomeButtonEnabled(true);
		ImageView view = (ImageView)findViewById(android.R.id.home);
		view.setPadding(0, 0, 50, 0); // I don't think hard coding this is an especially great idea, so I'll change it when I
		// switch to actionbarsherlock.
	}
	
	 /**
     * Sets all the text fields in the corresponding layout.
     */
    private void setFields() {
            image = (ImageView) findViewById(R.id.image);
            image.setImageBitmap(_contact.getImage());
            first = (EditText)findViewById(R.id.editFirstName);
            first.setText(_contact.getFirstName(), TextView.BufferType.EDITABLE);
            last = (EditText)findViewById(R.id.editLastName);
            last.setText(_contact.getLastName(), TextView.BufferType.EDITABLE);
            mobile = (EditText)findViewById(R.id.editMobilePhone);
            mobile.setText(_contact.getMobilePhone(), TextView.BufferType.EDITABLE);
            home = (EditText)findViewById(R.id.editHomePhone);
            home.setText(_contact.getHomePhone(), TextView.BufferType.EDITABLE);
            work = (EditText)findViewById(R.id.editWorkPhone);
            work.setText(_contact.getWorkPhone(), TextView.BufferType.EDITABLE);
            email = (EditText)findViewById(R.id.editEmail);
            email.setText(_contact.getEmail(), TextView.BufferType.EDITABLE);
            address = (EditText)findViewById(R.id.editAddress);
            address.setText(_contact.getAddress(), TextView.BufferType.EDITABLE);
            dob = (EditText)findViewById(R.id.editDOB);
            dob.setText(_contact.getDOB(), TextView.BufferType.EDITABLE);
            
            // Set the other headings
            TextView first2 = (TextView)findViewById(R.id.firstName2);
            first2.setText(_contact.getFirstName());
            TextView last2 = (TextView)findViewById(R.id.lastName2);
            last2.setText(_contact.getLastName());
            TextView mobile2 = (TextView)findViewById(R.id.mobilePhone2);
            mobile2.setText(_contact.getMobilePhone());
            TextView home2 = (TextView)findViewById(R.id.homePhone2);
            home2.setText(_contact.getHomePhone());
            TextView work2 = (TextView)findViewById(R.id.workPhone2);
            work2.setText(_contact.getWorkPhone());
            TextView email2 = (TextView)findViewById(R.id.email2);
            email2.setText(_contact.getEmail());
            TextView address2 = (TextView)findViewById(R.id.address2);
            address2.setText(_contact.getAddress());
            TextView dob2 = (TextView)findViewById(R.id.dob2);
            dob2.setText(_contact.getDOB());
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}
	
	/**
	 * Provides different actions that are executed when an actionbar
	 * action is selected.
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_save:
			saveContact();
			if (_activity.equals("add")) {
				Intent toContact = new Intent(this, ContactView.class);
				toContact.putExtra("contactID", _id);
				startActivity(toContact);
			}
			finish();
			return true;
		case android.R.id.home: // I.e. cancel (I'm using the home button for convenience)
			// do other stuff
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * Saves the data from the views to the database.
	 */
	public void saveContact() {
		final ContactList ls = new ContactList(this);
		String f = first.getText().toString();
		String l = last.getText().toString();
		String e = email.getText().toString();
		String w = work.getText().toString();
		String h = home.getText().toString();
		String m = mobile.getText().toString();
		String a = address.getText().toString();
		String d = dob.getText().toString();
		ls.editContact(_id, f, l, e, h, m, w, d, _image, a);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			image.setImageBitmap(photo);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
			_image = stream.toByteArray();
		}
	}

	/* Methods that deal with clicks. Should refactor by creating a generator to aid maintenance. */
	
	/**
	 * Starts the camera app when the ImageView is clicked.
	 * @param v = the ImageView
	 */
	public void imageClicked(View v) {
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);
	}
	
	//protected void
	
	/**
	 * Identifies whether the corresponding textbox to the first name heading is visible and either shows or hides
	 * it as relevant.
	 * @param v = the view clicked
	 */
	public void firstNameClicked(View v) {
		View box = findViewById(R.id.editFirstName);	
		if (box.getVisibility() != View.VISIBLE) { // EditText is invisible. Expand it.
			findViewById(R.id.editFirstName).setVisibility(View.VISIBLE);
			_imm.showSoftInput(box, 0); // This line isn't really necessary because of the next 2, but I left it in just to be safe.
			
			// These must be included or else you have to click the view thrice for the keyboard to show. Not entirely
			// sure why this is, but others have had the same problem switching from GONE to VISIBLE.
			box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
            box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0)); 
		} else { // Collapse the EditText
			findViewById(R.id.editFirstName).setVisibility(View.GONE);
			_imm.hideSoftInputFromWindow(box.getWindowToken(), 0);
			((TextView) findViewById(R.id.firstName2)).setText(first.getText().toString());
		}
	}
		
	/**
	 * Identifies whether the corresponding textbox to the last name heading is visible and either shows or hides
	 * it as relevant.
	 * @param v = the view clicked
	 */
	public void lastNameClicked(View v) {
		View box = findViewById(R.id.editLastName);
		if (box.getVisibility() != View.VISIBLE) {
			findViewById(R.id.editLastName).setVisibility(View.VISIBLE);
			_imm.showSoftInput(box, 0);
			box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
            box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0)); 
		} else {
			findViewById(R.id.editLastName).setVisibility(View.GONE);
			_imm.hideSoftInputFromWindow(box.getWindowToken(), 0);
			((TextView) findViewById(R.id.lastName2)).setText(last.getText().toString());
		}
	}
	
	/**
	 * Identifies whether the corresponding textbox to the mobile heading is visible and either shows or hides
	 * it as relevant.
	 * @param v = the view clicked
	 */
	public void mobilePhoneClicked(View v) {
		View box = findViewById(R.id.editMobilePhone);
		if (box.getVisibility() != View.VISIBLE) {
			findViewById(R.id.editMobilePhone).setVisibility(View.VISIBLE);
			_imm.showSoftInput(box, 0);
			box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
            box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0)); 
		} else {
			findViewById(R.id.editMobilePhone).setVisibility(View.GONE);
			_imm.hideSoftInputFromWindow(box.getWindowToken(), 0);
			((TextView) findViewById(R.id.mobilePhone2)).setText(mobile.getText().toString());
		}
	}
	
	/**
	 * Identifies whether the corresponding textbox to the home phone heading is visible and either shows or hides
	 * it as relevant.
	 * @param v = the view clicked
	 */
	public void homePhoneClicked(View v) {
		View box = findViewById(R.id.editHomePhone);
		if (box.getVisibility() != View.VISIBLE) {
			findViewById(R.id.editHomePhone).setVisibility(View.VISIBLE);
			_imm.showSoftInput(box, 0);
			box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
            box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0)); 
		} else {
			findViewById(R.id.editHomePhone).setVisibility(View.GONE);
			_imm.hideSoftInputFromWindow(box.getWindowToken(), 0);
			((TextView) findViewById(R.id.homePhone2)).setText(home.getText().toString());
		}
	}
	
	/**
	 * Identifies whether the corresponding textbox to the work phone heading is visible and either shows or hides
	 * it as relevant.
	 * @param v = the view clicked
	 */
	public void workPhoneClicked(View v) {
		View box = findViewById(R.id.editWorkPhone);
		if (box.getVisibility() != View.VISIBLE) {
			findViewById(R.id.editWorkPhone).setVisibility(View.VISIBLE);
			_imm.showSoftInput(box, 0);
			box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
            box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0)); 
		} else {
			findViewById(R.id.editWorkPhone).setVisibility(View.GONE);
			_imm.hideSoftInputFromWindow(box.getWindowToken(), 0);
			((TextView) findViewById(R.id.workPhone2)).setText(work.getText().toString());
		}
	}
	
	/**
	 * Identifies whether the corresponding textbox to the email heading is visible and either shows or hides
	 * it as relevant.
	 * @param v = the view clicked
	 */
	public void emailClicked(View v) {
		View box = findViewById(R.id.editEmail);
		if (box.getVisibility() != View.VISIBLE) {
			findViewById(R.id.editEmail).setVisibility(View.VISIBLE);
			_imm.showSoftInput(box, 0);
			box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
            box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0)); 
		} else {
			findViewById(R.id.editEmail).setVisibility(View.GONE);
			_imm.hideSoftInputFromWindow(box.getWindowToken(), 0);
			((TextView) findViewById(R.id.email2)).setText(email.getText().toString());
		}
	}
	
	/**
	 * Identifies whether the corresponding textbox to the address heading is visible and either shows or hides
	 * it as relevant.
	 * @param v = the view clicked
	 */
	public void addressClicked(View v) {
		View box = findViewById(R.id.editAddress);
		if (box.getVisibility() != View.VISIBLE) {
			findViewById(R.id.editAddress).setVisibility(View.VISIBLE);
			_imm.showSoftInput(box, 0);
			box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
            box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0)); 
		} else {
			findViewById(R.id.editAddress).setVisibility(View.GONE);
			_imm.hideSoftInputFromWindow(box.getWindowToken(), 0);
			((TextView) findViewById(R.id.address2)).setText(address.getText().toString());
		}
	}
	
	/**
	 * Identifies whether the corresponding textbox to the date of birth heading is visible and either shows or hides
	 * it as relevant.
	 * @param v = the view clicked
	 */
	public void dobClicked(View v) {
		View box = findViewById(R.id.editDOB);
		if (box.getVisibility() != View.VISIBLE) {
			findViewById(R.id.editDOB).setVisibility(View.VISIBLE);
			_imm.showSoftInput(box, 0);	
			box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
            box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0)); 
		} else {
			findViewById(R.id.editDOB).setVisibility(View.GONE);
			_imm.hideSoftInputFromWindow(box.getWindowToken(), 0);
			((TextView) findViewById(R.id.dob2)).setText(dob.getText().toString());
		}
	}
	
}
