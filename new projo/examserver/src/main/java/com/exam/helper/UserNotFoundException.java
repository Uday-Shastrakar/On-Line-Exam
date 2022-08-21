package com.exam.helper;

public class UserNotFoundException extends Exception {

	public UserNotFoundException() {
		super("User with this username not found in database !! ");
		// TODO Auto-generated constructor stub
	}

	public UserNotFoundException(String msg) {super(msg);}
	
	
}
