package com.reservationapp.business.service;

import java.util.Optional;
import java.util.Set;

import com.reservationapp.business.service.exception.CorruptedRequestException;
import com.reservationapp.business.service.exception.UserNotFoundException;
import com.reservationapp.persistance.entity.Reservation;
import com.reservationapp.persistance.entity.User;

public interface UserService {
	
	public Set<User> getAllUsers();
	public void createUser(User user);
	public void deleteUser(String userEmail) throws UserNotFoundException;
	public Optional<User> findByFirstName(String firstName);
	public void save(User user);
	public Optional<User> findByEmail(String email);
	public void validateRequestSender(User user, String jwt) throws CorruptedRequestException;
	public Set<Reservation> getUserReservation(String email) throws UserNotFoundException;
	public void updateUser(User updatedUser, String oldUserEmail);
	
}
