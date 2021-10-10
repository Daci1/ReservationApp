package com.reservationapp.business.implementation;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservationapp.business.service.UserService;
import com.reservationapp.business.service.exception.CorruptedRequestException;
import com.reservationapp.persistance.entity.Reservation;
import com.reservationapp.persistance.entity.User;
import com.reservationapp.persistance.repository.UserRepository;
import com.reservationapp.security.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private JwtUtil jwtUtil;
	@Override
	public Set<User> getAllUsers() {
		Set<User> allUsers = new HashSet<>();
		userRepo.findAll().forEach(user -> allUsers.add(user));
		return allUsers;
	}

	@Override
	public void createUser(User user) {
		userRepo.save(user);
	}

	@Override
	public void deleteUser(User user) {
		userRepo.delete(user);
	}

	@Override
	public Optional<User> findByFirstName(String firstName) {
		return userRepo.findByFirstName(firstName);
	}

	@Override
	public void save(User user) {
		userRepo.save(user);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public void validateRequestSender(User user, String jwt) throws CorruptedRequestException {
		String jwtUsername = jwtUtil.extractUsername(jwt);
		if(!user.getUsername().equals(jwtUsername)) {
			throw new CorruptedRequestException();
		}
	}	
	
}
