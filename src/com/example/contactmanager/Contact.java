package com.example.contactmanager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Class representing a contact, used to make access and manipulation of
 * database information more convenient and understandable.
 * 
 * @author Harriet Robinson-Chen
 * 
 */
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
	private long _id; // ID for indexing in the database.

	/**
	 * Image must be initialised to null to avoid errors.
	 */
	public Contact() {
		_image = null;
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
	 * 
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
	 * 
	 * @return = work phone number as a string
	 */
	public String getWorkPhone() {
		return _workPhone;
	}

	/**
	 * Returns the contact's home phone number.
	 * 
	 * @return = home phone number as a string
	 */
	public String getHomePhone() {
		return _homePhone;
	}

	/**
	 * Returns the contact's mobile phone number.
	 * 
	 * @return = mobile phone number as a string
	 */
	public String getMobilePhone() {
		return _mobilePhone;
	}

	/**
	 * Returns the contact's full name. Here for convenience.
	 * 
	 * @return = the contact's first and last names if they exist.
	 */
	public String getFullName() {
		return _firstName + " " + _lastName;
	}

	/**
	 * Returns the contact's email address.
	 * 
	 * @return = the email address as a string.
	 */
	public String getEmail() {
		return _email;
	}

	/**
	 * Returns the 'primary' number (home or mobile - mobile will take
	 * precedence) It would be good if the user could set this.
	 * 
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
	 * Returns the contact's picture if it exists, converting a byte array to a
	 * bitmap array.
	 * 
	 * @return = a bitmap array representing the image.
	 */
	public Bitmap getImage() {
		if (_image != null) {
			InputStream inp = new ByteArrayInputStream(_image);
			Bitmap bmp = BitmapFactory.decodeStream(inp);
			return bmp;
		} else {
			return null;
		}
	}

	/**
	 * Returns the contact's image as a byte array.
	 * 
	 * @return
	 */
	public byte[] getImageByte() {
		return _image;
	}

	/**
	 * Return's the contact's ID for the database.
	 * 
	 * @return
	 */
	public long getId() {
		return _id;
	}

	/* SETTERS */

	/**
	 * Sets the contact's ID
	 * 
	 * @param id
	 */
	public void setID(long id) {
		_id = id;
	}

	/**
	 * Sets the contact's date of birth.
	 * 
	 * @param dob
	 */
	public void setDOB(String dob) {
		_dob = dob;
	}

	/**
	 * Sets the contact's address.
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		_address = address;
	}

	/**
	 * Sets the contact's first name.
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	/**
	 * Sets the contact's last name.
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		_lastName = lastName;
	}

	/**
	 * Sets the contact's work phone number.
	 * 
	 * @param workPhone
	 */
	public void setWorkPhone(String workPhone) {
		_workPhone = workPhone;
	}

	/**
	 * Sets the contact's home phone number.
	 * 
	 * @param homePhone
	 */
	public void setHomePhone(String homePhone) {
		_homePhone = homePhone;
	}

	/**
	 * Sets the contact's mobile phone number.
	 * 
	 * @param mobilePhone
	 */
	public void setMobilePhone(String mobilePhone) {
		_mobilePhone = mobilePhone;
	}

	/**
	 * Sets the contact's email address.
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		_email = email;
	}

	/**
	 * From a byte array, sets the contact's image.
	 * 
	 * @param image
	 */
	public void setImage(byte[] image) {
		_image = image;
	}

}