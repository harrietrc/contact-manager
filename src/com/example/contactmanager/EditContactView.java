package com.example.contactmanager;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.KeyEvent;
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
	private boolean _fieldExpandedFlag;
	private DatabaseHelper _c;
	
	private ImageView image;
	private EditText first;
	private EditText last;
	private EditText mobile;
	private EditText home;
	private EditText work;
	private EditText email;
	private EditText address;
	private EditText dob;
	
	// other headings
	private TextView first2;
	private TextView last2;
	private TextView mobile2;
	private TextView home2;
	private TextView work2;
	private TextView email2;
	private TextView address2;
	private TextView dob2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_contact);
		
		// Set the flag that indicates whether an EditText view is open to false
		_fieldExpandedFlag = false;
		
		// Sets up the keyboard
		_imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
		
		/* Here I looked into how to pass the contact to the new activity. I'm not really sure how the contacts
		 * database is going to work and suspect we'll be learning a bit about databases later. Because of this 
		 * I was unwilling to fiddle around with serials and parcels so for now I'm just going to create a new
		 * object. */
		//mockContact();
		setFields();
		initialiseActionBar();
	}
	
	/**
	 * Initialises the action bar.
	 */
	private void initialiseActionBar()
	{
		final ActionBar actionBar = getActionBar();
		actionBar.setTitle("Editing "+ _contact.getFullName());
		// 
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
		image.setImageResource(_contact.getImageId());
		first.setText(_contact.getFirstName(), TextView.BufferType.EDITABLE);
		last.setText(_contact.getLastName(), TextView.BufferType.EDITABLE);
		mobile.setText(_contact.getMobilePhone(), TextView.BufferType.EDITABLE);
		home.setText(_contact.getHomePhone(), TextView.BufferType.EDITABLE);
		work.setText(_contact.getWorkPhone(), TextView.BufferType.EDITABLE);
		email.setText(_contact.getEmail(), TextView.BufferType.EDITABLE);
		address.setText(_contact.getAddress(), TextView.BufferType.EDITABLE);
		dob.setText(_contact.getDOB(), TextView.BufferType.EDITABLE);
		
		// Set the other headings
		first2.setText(_contact.getFirstName());
		last2.setText(_contact.getLastName());
		mobile2.setText(_contact.getMobilePhone());
		home2.setText(_contact.getHomePhone());
		work2.setText(_contact.getWorkPhone());
		email2.setText(_contact.getEmail());
		address2.setText(_contact.getAddress());
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
			finish();
			return true;
		case android.R.id.home:
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
		String f = first.getText().toString();
		String l = first.getText().toString();
		String e = email.getText().toString();
		String w = work.getText().toString();
		String h = home.getText().toString();
		String m = mobile.getText().toString();
		String a = address.getText().toString();
		String d = dob.getText().toString();
		//String i = image.getText().toString();
		_c.editContact(0, f, l, e, h, m, w, d, null, a);
	}

	/* Methods that deal with clicks. Not the best way to do this, so I may rejig it if I find a better way. 
	 * These are all a bit buggy in that if you open two fields and then close one the keyboard will be hidden.
	 * The basic functionality is there, at least. */
	
	public void imageClicked(View v) {
		// blank for now. Not sure how the 'choose image' dialogue is going to work, or how images in general
		// will work.
	}
	
	/**
	 * Identifies whether the corresponding textbox to the first name heading is visible and either shows or hides
	 * it as relevant.
	 * @param v = the view clicked
	 */
	public void firstNameClicked(View v) {
		View box = findViewById(R.id.editFirstName);	
		if (box.getVisibility() != View.VISIBLE) {
			findViewById(R.id.editFirstName).setVisibility(View.VISIBLE);
			_imm.showSoftInput(box, 0); // This line isn't really necessary because of the next 2, but I left it in just to be safe.
			
			// These must be included or else you have to click the view thrice for the keyboard to show. Not entirely
			// sure why this is, but others have had the same problem switching from GONE to VISIBLE.
			box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
            box.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0)); 
		} else {
			findViewById(R.id.editFirstName).setVisibility(View.GONE);
			_imm.hideSoftInputFromWindow(box.getWindowToken(), 0);
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
		}
	}
	
}
