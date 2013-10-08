package com.example.contactmanager;

public class Contact {
	
	private String _firstName;
	private String _lastName;
	private String _home;
	private String _work;
	private String _mobile;
	private String _address;
	private String _dob;
	// There will also be an image field, but I'm not really sure how images
	// are going to be set up for this project.
	
	/**
	 * Blank for now. Obviously won't be later.
	 */
	public Contact() {
		
	}
	
	/* GETTERS */
	
	/**
	 * Returns the contact's date of birth. I'll probably sort out how to
	 * uniformly format these later.
	 * @return = date of birth
	 */
	public String getDOB() {
		return "22/10/1993";
	}
	
	/**
	 * Returns the contact's home address.
	 * @return = home address
	 */
	public String getAddress() {
		return "42 Wallaby Way, Sydney";
	}
	
	/**
	 * Returns the contact's first name.
	 * @return = first name
	 */
	public String getFirstName() {
		return "John";
	}
	
	/**
	 * Returns the contact's last name.
	 * @return = last name
	 */
	public String getLastName() {
		return "Doe";
	}
	
	/**
	 * Returns the contact's work phone. May implement automatic
	 * formatting later.
	 * @return = work phone number as a string
	 */
	public String getWorkPhone() {
		return "222222222";
	}
	
	/**
	 * Returns the contact's home phone number.
	 * @return = home phone number as a string
	 */
	public String getHomePhone() {
		return "333333333";
	}
	
	/**
	 * Returns the contact's mobile phone number.
	 * @return = mobile phone number as a string
	 */
	public String getMobilePhone() {
		return "123456789";
	}

	/**
	 * Returns the contact's full name. Here for convenience.
	 * @return = the contact's first and last names if they exist.
	 */
	public String getFullName() {
		return "John Doe";
	}
	
	public String getEmail() {
		return "dodo@gmail.com";
	}

	/**
	 * Returns the 'primary' number (home or mobile - mobile will
	 * take precedence)
	 * @return = the contact's primary number.
	 */
	public String getPrimaryNumber() {
		return "123456789";
	}

	/**
	 * Returns the contact's picture if it exists.
	 * @return = the ID of the contact's picture.
	 */
	public int getImageId() {
		return R.drawable.man;
	}
	
	/* SETTERS */
	
	public void setDOB() {
		//
	}
	
	public void setAddress() {
		//
	}
	
	public void setFirstName() {
		//
	}
	
	public void setLastName() {
		//
	}
	
	public void setWorkPhone() {
		//
	}
 
	public void setHomePhone() {
		//
	}
	
	public void setMobilePhone() {
		//
	}
	
	public void setEmail() {
		//
	}

	public void setImageId() {
		//
	}

}
