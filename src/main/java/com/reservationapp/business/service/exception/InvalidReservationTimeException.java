package com.reservationapp.business.service.exception;

public class InvalidReservationTimeException extends Exception {
	public InvalidReservationTimeException() {
		super("Invalid reservation begin time!");
	}
}
