package com.reservationapp.presentation.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservationapp.business.implementation.UserDetailsServiceImpl;
import com.reservationapp.business.service.ReservationService;
import com.reservationapp.business.service.UserService;
import com.reservationapp.business.service.exception.CorruptedRequestException;
import com.reservationapp.model.AuthenticationRequest;
import com.reservationapp.persistance.entity.Reservation;
import com.reservationapp.persistance.entity.User;
import com.reservationapp.security.util.JwtUtil;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAllUsers(@RequestHeader String authorization){
		try {
			Optional<User> user = userService.findByEmail(jwtTokenUtil.extractUsername(authorization));
			if(user.isPresent() && user.get().getRole().equalsIgnoreCase("ADMIN")) {
				return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>("The sender is not an admin!",HttpStatus.FORBIDDEN);
			}
		}catch (Exception e) {
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
	
	@RequestMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
			final String jwt = jwtTokenUtil.generateToken(userDetails);
			return ResponseEntity.ok(jwt);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
