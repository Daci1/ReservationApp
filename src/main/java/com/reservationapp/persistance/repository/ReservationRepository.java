package com.reservationapp.persistance.repository;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.reservationapp.persistance.entity.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Integer>{
	public Optional<Reservation> findByTableNameAndReservationBegin(String tableName, Timestamp reservationBegin);
	public Set<Reservation> findByTableNameAndReservationBeginBetween(String tableName, Timestamp beginOfDay, Timestamp endOfDay);
}
