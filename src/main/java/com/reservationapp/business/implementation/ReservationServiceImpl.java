package com.reservationapp.business.implementation;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservationapp.business.service.ReservationService;
import com.reservationapp.persistance.entity.Reservation;
import com.reservationapp.persistance.repository.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService{
	
	@Autowired
	private ReservationRepository reservationRepo;

	@Override
	public Set<Reservation> getAllReservations() {
		Set<Reservation> allReservations = new HashSet<>();
		reservationRepo.findAll().forEach(reservation -> allReservations.add(reservation));
		return allReservations;
	}

	@Override
	public void createReservation(Reservation reservation) {
		reservationRepo.save(reservation);
	}

	@Override
	public void save(Reservation reservation) {
		reservationRepo.save(reservation);
	}
}
