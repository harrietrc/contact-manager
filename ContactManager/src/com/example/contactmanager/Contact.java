package com.example.contactmanager;

public class Contact {
	
	private String _firstName;
	private String _lastName;
	
	public Contact() {
		
	}

	public String getFullName() {
		return "John Doe";
	}

	public String getPrimaryNumber() {
		return "123456789";
	}

	public int getImageId() {
		return R.drawable.man;
	}

}
