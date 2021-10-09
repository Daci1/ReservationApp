package com.reservationapp.business.service;

import java.util.Set;

import com.reservationapp.persistance.entity.Reservation;

public interface ReservationService {

	public Set<Reservation> getAllReservations();
	public void createReservation(Reservation reservation);
	public void save(Reservation reservation);
}
