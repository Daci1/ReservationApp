package com.reservationapp.presentation.controller;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping("/addreservation")
	public ResponseEntity<Void> addReservationToUser(@RequestBody Map<String, String> Json){
		//TODO: add logic+verifications for adding the reservation
		try {
			String email;
			Timestamp reservationBegin;
			String tableNumber;
			if(Json.containsKey("email") && Json.containsKey("reservationBegin") && Json.containsKey("tableNumber")) {
				email = Json.get("email");
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date parsedDate = dateFormat.parse(Json.get("reservationBegin"));
				reservationBegin = new Timestamp(parsedDate.getTime());
				
				tableNumber = Json.get("tableNumber");
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			Optional<User> user = userService.findByEmail(email);
			if(user.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else {
				if(reservationService.findReservation(tableNumber, reservationBegin).isPresent()){
					return new ResponseEntity<>(HttpStatus.CONFLICT);
				}else {
					Reservation reservation = new Reservation(tableNumber, reservationBegin);
					reservationService.save(reservation);
					user.get().addReservation(reservation);
					userService.save(user.get());
					return new ResponseEntity<>(HttpStatus.OK);
				}
				
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user){
		System.out.println(user);
		try {
			Optional<User> alreadyRegisteredUser = userService.findByEmail(user.getEmail());
			if(alreadyRegisteredUser.isPresent()) {
				return new ResponseEntity<>(HttpStatus.FOUND);
			}else {
				userService.save(user);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/login")
	public ResponseEntity<User> loginUser(@RequestBody Map<String, String> Json){
		try {
			String email, password;
			if(Json.containsKey("email") && Json.containsKey("password")) {
				email = Json.get("email");
				password = Json.get("password");
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			Optional<User> loginUser = userService.findByEmail(email);
			if(loginUser.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else {
				if(loginUser.get().getPassword().equals(password)) {
					return new ResponseEntity<>(loginUser.get(), HttpStatus.OK);
				}else {
					return new ResponseEntity<>(HttpStatus.CONFLICT);
				}
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
