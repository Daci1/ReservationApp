package com.reservationapp.presentation.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservationapp.business.service.ReservationService;
import com.reservationapp.business.service.UserService;
import com.reservationapp.business.service.exception.CorruptedRequestException;
import com.reservationapp.business.service.exception.InvalidReservationTimeException;
import com.reservationapp.business.service.exception.UserNotFoundException;
import com.reservationapp.persistance.entity.Reservation;
import com.reservationapp.persistance.entity.User;
import com.reservationapp.security.util.JwtUtil;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

	@Autowired
	private UserService userService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@PostMapping("/addreservation")
	public ResponseEntity<?> addReservationToUser(@RequestBody Map<String, String> Json, @RequestHeader String Authorization){
		try {
			String email;
			Timestamp reservationBegin;
			String tableNumber;
			if(Json.containsKey("email") && Json.containsKey("reservationBegin") && Json.containsKey("tableNumber")) {
				email = Json.get("email");
				email = email.replace("%40", "@");
				email = email.replace("=", "");
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
				userService.validateRequestSender(user.get(), Authorization);
				if(reservationService.findReservation(tableNumber, reservationBegin).isPresent()){
					return new ResponseEntity<>("Already reserved!", HttpStatus.CONFLICT);
				}else {
					reservationService.addNewReservation(user.get(), reservationBegin, tableNumber);
					return new ResponseEntity<>(HttpStatus.OK);
				}
			}
		}catch (CorruptedRequestException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
		}catch (InvalidReservationTimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping("/findreservationbyday")
	public ResponseEntity<?> findReservationByDay(@RequestBody Map<String, String> Json){
		try {
			if(!Json.containsKey("tableName") || !Json.containsKey("day")) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			LocalDateTime dateTime = LocalDate.parse(Json.get("day")).atStartOfDay();
			String tableName = Json.get("tableName");
			Timestamp day = Timestamp.valueOf(dateTime);
			return new ResponseEntity<>(reservationService.findReservationByDayAndTableName(day, tableName) ,HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping("/getuserreservations")
	public ResponseEntity<?> getUserReservations(@RequestBody String email){
		email = email.replace("%40", "@");
		email = email.replace("=", "");
		try {
			Set<Reservation> userReservations = userService.getUserReservation(email.trim());
			return new ResponseEntity<>(userReservations, HttpStatus.OK);
		}catch (UserNotFoundException userNotFound) {
			userNotFound.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping("/deletereservation")
	public ResponseEntity<?> deleteReservation(@RequestHeader String authorization, @RequestBody Map<String, String> Json){
		try {
			Optional<User> user = userService.findByEmail(jwtTokenUtil.extractUsername(authorization));
			if(user.isPresent() && Json.containsKey("reservationBegin") && Json.containsKey("tableNumber")) {
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date parsedDate = dateFormat.parse(Json.get("reservationBegin"));
				Timestamp reservationBegin = new Timestamp(parsedDate.getTime());
				
				String tableNumber = Json.get("tableNumber");
				
				Reservation reservation = new Reservation(tableNumber, reservationBegin);
				reservationService.deleteReservation(user.get(), reservation);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Invalid.",HttpStatus.FORBIDDEN);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@RequestMapping("/getall")
	public ResponseEntity<?> getAllReservations(@RequestHeader String authorization){
		try {
			Optional<User> user = userService.findByEmail(jwtTokenUtil.extractUsername(authorization));
			if(user.isPresent() && user.get().getRole().equalsIgnoreCase("ADMIN")) {
				return new ResponseEntity<>(reservationService.getAllReservations(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Invalid.",HttpStatus.FORBIDDEN);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
