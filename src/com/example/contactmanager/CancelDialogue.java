package com.example.contactmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Prompts the user to cancel any changes or close the dialogue box and return
 * to the activity.
 * 
 * @author Harriet Robinson-Chen
 * 
 */
public class CancelDialogue extends DialogFragment {

	public CancelDialogue() {
		super();
	}

	/**
	 * On create, prompt the user to either cancel changes or return to the
	 * activity.
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Set up the variables
		final ContactList ls = new ContactList(getActivity());
		final String intent = getArguments().getString("intent");
		final long id = getArguments().getLong("contactID");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.cancel_prompt)
				// Delete the contact
				.setPositiveButton(R.string.yes,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								// If this was a new contact, delete it.
								if (intent.equals("add")) {
									ls.deleteContactByID(id);
								} else {
									// We must be editing, and need to
									// transition back to the 'view contact'
									// class.
									Intent toContact = new Intent(
											getActivity(), ContactView.class);
									toContact.putExtra("contactID", id);
									startActivity(toContact);
								}

								// Finish, discarding changes.
								getActivity().finish();
							}
						})
				// Cancel deletion action and return to activity
				.setNegativeButton(R.string.no,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
		return builder.create();
	}
}