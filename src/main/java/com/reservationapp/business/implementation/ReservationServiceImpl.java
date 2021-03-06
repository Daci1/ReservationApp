package com.reservationapp.business.implementation;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservationapp.business.service.ReservationService;
import com.reservationapp.business.service.exception.InvalidReservationTimeException;
import com.reservationapp.persistance.entity.Reservation;
import com.reservationapp.persistance.entity.User;
import com.reservationapp.persistance.repository.ReservationRepository;
import com.reservationapp.persistance.repository.UserRepository;

@Service
public class ReservationServiceImpl implements ReservationService{
	
	@Autowired
	private ReservationRepository reservationRepo;
	@Autowired
	private UserRepository userRepo;

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

	@Override
	public Optional<Reservation> findReservation(String tableName, Timestamp reservationBegin) {
		return reservationRepo.findByTableNameAndReservationBegin(tableName, reservationBegin);
	}

	@Override
	public void addNewReservation(User user, Timestamp reservationBegin, String tableName) throws InvalidReservationTimeException{
		if(reservationBegin.getHours()%2 != 0 || reservationBegin.getMinutes() != 0 || reservationBegin.getSeconds() != 0) {
			throw new InvalidReservationTimeException();
		}
		Reservation newReservation = new Reservation(tableName, reservationBegin);
		newReservation.setUser(user);
		reservationRepo.save(newReservation);
		user.addReservation(newReservation);
		userRepo.save(user);
	}

	@Override
	public Set<Reservation> findReservationByDayAndTableName(Timestamp day, String tableName) {
		Set<Reservation> reservations = new HashSet<>();
		LocalDateTime startOfDay = day.toLocalDateTime().toLocalDate().atStartOfDay();
		LocalDateTime endOfDay = startOfDay.plusDays(1L);
		reservationRepo.findByTableNameAndReservationBeginBetween(tableName, Timestamp.valueOf(startOfDay), Timestamp.valueOf(endOfDay))
						.forEach(reservation -> reservations.add(reservation));
		return reservations;
	}

	@Override
	public void deleteReservation(User user, Reservation reservation) {
		Optional<Reservation> searchedReservation = reservationRepo.findByTableNameAndReservationBegin(reservation.getTableNumber(), reservation.getReservationBegin());
		if(searchedReservation.isEmpty()) {
			return;
		}
		Reservation deleteReservation = searchedReservation.get();
		Set<Reservation> userReservations = deleteReservation.getUser().getUserReservation();
		if(userReservations.contains(deleteReservation) && (deleteReservation.getUser().equals(user) || user.getRole().equalsIgnoreCase("Admin"))){
			userReservations.remove(deleteReservation);
			reservationRepo.delete(deleteReservation);
			userRepo.save(deleteReservation.getUser());
		}
	}
}
