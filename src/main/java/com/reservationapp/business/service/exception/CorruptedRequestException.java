package com.reservationapp.business.service.exception;

public class CorruptedRequestException extends Exception{
	public CorruptedRequestException() {
		super("Request senders token doesn't match the sender credentials!");
	}
}
