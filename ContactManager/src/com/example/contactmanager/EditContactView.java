package com.example.contactmanager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EditContactView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_contact);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_contact_view, menu);
		return true;
	}

}
