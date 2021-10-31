package com.reservationapp.presentation.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservationapp.business.service.MenuEntryService;
import com.reservationapp.business.service.UserService;
import com.reservationapp.business.service.exception.MenuEntryAlreadyExistsException;
import com.reservationapp.business.service.exception.MenuEntryNotFoundException;
import com.reservationapp.persistance.entity.MenuEntry;
import com.reservationapp.persistance.entity.User;
import com.reservationapp.security.util.JwtUtil;

@RestController
@RequestMapping("/api/menu")
public class MenuEntryController {

	@Autowired
	private MenuEntryService menuEntryService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private UserService userService;

	@GetMapping("/getall")
	public ResponseEntity<?> getAllMenuEntries() {
		try {
			return new ResponseEntity<>(menuEntryService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/newMenuEntry")
	public ResponseEntity<?> newMenuEntry(@RequestHeader String authorization, @RequestBody MenuEntry menuEntry) {
		try {
			Optional<User> user = userService.findByEmail(jwtTokenUtil.extractUsername(authorization));
			if(user.isPresent() && user.get().getRole().equalsIgnoreCase("ADMIN")) {
				menuEntryService.createNewMenuEntry(menuEntry.getPrice(), menuEntry.getDescription(),
						menuEntry.getQuantity(), menuEntry.getProductName(), menuEntry.getCategory());
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>("The sender is not an admin!",HttpStatus.FORBIDDEN);
			}
			
		} catch (MenuEntryAlreadyExistsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/editMenuEntry")
	public ResponseEntity<?> editMenuEntry(@RequestHeader String authorization, @RequestBody MenuEntry menuEntry) {
		try {
			Optional<User> user = userService.findByEmail(jwtTokenUtil.extractUsername(authorization));
			if(user.isPresent() && user.get().getRole().equalsIgnoreCase("ADMIN")) {
				menuEntryService.editMenuEntry(menuEntry.getPrice(), menuEntry.getDescription(),
						menuEntry.getQuantity(), menuEntry.getProductName(), menuEntry.getCategory());
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>("The sender is not an admin!",HttpStatus.FORBIDDEN);
			}
		} catch (MenuEntryNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping("/deleteMenuEntry")
	public ResponseEntity<?> deleteMenuEntry(@RequestBody MenuEntry menuEntry){
		try {
			Optional<MenuEntry> searchedMenuEntry = menuEntryService.getMenuEntryByProductName(menuEntry.getProductName());
			if(searchedMenuEntry.isPresent()){
				menuEntryService.deleteMenuEntry(searchedMenuEntry.get());
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
