package com.example.contactmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Prompts the user to cancel any changes or close the dialogue box and return to the activity.
 * @author Harriet Robinson-Chen
 *
 */
public class CancelDialogue extends DialogFragment {
	
	private String _intent; // 'add' or 'edit': indicates whether this was launched from an 'add contact' activity or an 'edit contact' activity
	private ContactList _ls; // A contact list
	private Contact _contact; // The contact being operated upon

	public CancelDialogue(String intent, Contact contact, ContactList ls) {
		super();
		_intent = intent;
		_ls = ls;
		_contact = contact;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				getActivity());
		builder.setMessage(R.string.cancel_prompt)
				// Delete the contact
				.setPositiveButton(R.string.yes,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(
									DialogInterface dialog,
									int which) {
								
								// If this was a new contact, delete it.
								if (_intent.equals("add")) {
									_ls.deleteContact(_contact);
								}
								
								// Finish, discarding changes.
								getActivity().finish();
							}
						})
				// Cancel deletion action and return to activity
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