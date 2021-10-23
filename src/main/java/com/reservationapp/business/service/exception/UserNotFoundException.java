package com.reservationapp.business.service.exception;

public class UserNotFoundException extends Exception{
	public UserNotFoundException() {
		super("User not found!");
	}
}
