package com.reservationapp.business.service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;

import com.reservationapp.business.service.exception.InvalidReservationTimeException;
import com.reservationapp.persistance.entity.Reservation;
import com.reservationapp.persistance.entity.User;

public interface ReservationService {

	public Set<Reservation> getAllReservations();
	public void createReservation(Reservation reservation);
	public void save(Reservation reservation);
	public Optional<Reservation> findReservation(String tableName, Timestamp reservationBegin);
	public void addNewReservation(User user, Timestamp reservationBegin, String tableName) throws InvalidReservationTimeException;
	public Set<Reservation> findReservationByDayAndTableName(Timestamp day, String tableName);
}
