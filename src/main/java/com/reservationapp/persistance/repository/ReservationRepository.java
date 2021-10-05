package com.reservationapp.persistance.repository;

import org.springframework.data.repository.CrudRepository;

import com.reservationapp.persistance.entity.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Integer>{

}
