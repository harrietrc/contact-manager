package com.example.contactmanager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Contact {

	private String _firstName;
	private String _lastName;
	private String _homePhone;
	private String _workPhone;
	private String _mobilePhone;
	private String _address;
	private String _dob;
	private String _email;
	private byte[] _image;
	private long _id;

	
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
	 * 
	 * @return = date of birth
	 */
	public String getDOB() {
		return _dob;
	}

	/**
	 * Returns the contact's home address.
	 * 
	 * @return = home address
	 */
	public String getAddress() {
		return _address;
	}

	/**
	 * Returns the contact's first name.
	 * @return = first name
	 */
	public String getFirstName() {
		return _firstName;
	}

	/**
	 * Returns the contact's last name.
	 * 
	 * @return = last name
	 */
	public String getLastName() {
		return _lastName;
	}

	/**
	 * Returns the contact's work phone. May implement automatic formatting
	 * later.
	 * @return = work phone number as a string
	 */
	public String getWorkPhone() {
		return _workPhone;
	}

	/**
	 * Returns the contact's home phone number.
	 * @return = home phone number as a string
	 */
	public String getHomePhone() {
		return _homePhone;
	}

	/**
	 * Returns the contact's mobile phone number.
	 * @return = mobile phone number as a string
	 */
	public String getMobilePhone() {
		return _mobilePhone;
	}

	/**
	 * Returns the contact's full name. Here for convenience.
	 * @return = the contact's first and last names if they exist.
	 */
	public String getFullName() {
		return _firstName + " " + _lastName;
	}

	/**
	 * Returns the contact's email address.
	 * @return = the email address as a string.
	 */
	public String getEmail() {
		return _email;
	}

	/**
	 * Returns the 'primary' number (home or mobile - mobile will take
	 * precedence)
	 * It would be good if the user could set this.
	 * @return = the contact's primary number.
	 */
	public String getPrimaryNumber() {
		if (_mobilePhone.length() != 0) {
			return _mobilePhone;
		} else if (_homePhone.length() != 0) {
			return _homePhone;
		} else {
			return _workPhone;
		}
	}

	/**
	 * Returns the contact's picture if it exists, converting
	 * a byte array to a bitmap array.
	 * @return = a bitmap array representing the image.
	 */
	public Bitmap getImage() {
		InputStream inp = new ByteArrayInputStream(_image);
		Bitmap bmp = BitmapFactory.decodeStream(inp);
		return bmp;
	}
	
	public byte[] getImageByte() {
		return _image;
	}
	
	public long getId() {
		return _id;
	}

	/* SETTERS */

	public void setID(long id) {
		_id = id;
	}

	public void setDOB(String dob) {
		_dob = dob;
	}

	public void setAddress(String address) {
		_address = address;
	}

	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	public void setLastName(String lastName) {
		_lastName = lastName;
	}

	public void setWorkPhone(String workPhone) {
		_workPhone = workPhone;
	}

	public void setHomePhone(String homePhone) {
		_homePhone = homePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		_mobilePhone = mobilePhone;
	}

	public void setEmail(String email) {
		_email = email;
	}

	public void setImage(byte[] image) {
		_image = image;
	}

}