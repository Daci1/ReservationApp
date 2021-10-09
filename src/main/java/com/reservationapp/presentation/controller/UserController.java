package com.reservationapp.presentation.controller;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservationapp.business.service.ReservationService;
import com.reservationapp.business.service.UserService;
import com.reservationapp.persistance.entity.Reservation;
import com.reservationapp.persistance.entity.User;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ReservationService reservationService;
	
	@GetMapping("/getall")
	public ResponseEntity<Set<Reservation>> getAllUsers(){
		return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
	}
	
	@PostMapping("/adduser")
	public ResponseEntity<Void> addUser(){
		//TODO: add logic+verifications for adding the user
		User user = new User("TestFirstName", "TestLastName", "testEmail@", "parola123", new Timestamp(System.currentTimeMillis()));
		userService.createUser(user);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping("/addreservation")
	public ResponseEntity<Void> addReservationToUser(){
		//TODO: add logic+verifications for adding the reservation
		Optional<User> user = userService.findByFirstName("TestFirstName");
		if(user.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}else {
			Reservation reservation = new Reservation(3, new Timestamp(System.currentTimeMillis()));
			reservationService.save(reservation);
			user.get().addReservation(reservation);
			userService.save(user.get());
			return new ResponseEntity(HttpStatus.OK);
		}
	}
}
