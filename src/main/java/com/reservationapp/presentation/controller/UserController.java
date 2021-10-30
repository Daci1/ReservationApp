package com.reservationapp.presentation.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservationapp.business.implementation.UserDetailsServiceImpl;
import com.reservationapp.business.service.ReservationService;
import com.reservationapp.business.service.UserService;
import com.reservationapp.model.AuthenticationRequest;
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
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping("/getall")
	public ResponseEntity<?> getAllUsers(@RequestHeader String authorization){
		try {
			Optional<User> user = userService.findByEmail(jwtTokenUtil.extractUsername(authorization));
			if(user.isPresent() && user.get().getRole().equalsIgnoreCase("ADMIN")) {
				return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>("The sender is not an admin!",HttpStatus.FORBIDDEN);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user){
		
		try {
			Optional<User> alreadyRegisteredUser = userService.findByEmail(user.getEmail());
			if(alreadyRegisteredUser.isPresent()) {
				return new ResponseEntity<>(HttpStatus.FOUND);
			}else {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				userService.save(user);
				
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/getUser")
	public ResponseEntity<User> getUser(@RequestBody AuthenticationRequest authenticationRequest){
		try {
			String email = authenticationRequest.getUsername();
			if(email == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			Optional<User> loginUser = userService.findByEmail(email);
			if(loginUser.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else{
				return new ResponseEntity<>(loginUser.get(), HttpStatus.OK);
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
		}catch (BadCredentialsException | InternalAuthenticationServiceException e) {
			return new ResponseEntity<>("Bad credentials!", HttpStatus.FORBIDDEN);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
