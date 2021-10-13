package com.reservationapp.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservationapp.business.service.MenuEntryService;
import com.reservationapp.business.service.exception.MenuEntryAlreadyExistsException;
import com.reservationapp.business.service.exception.MenuEntryNotFoundException;
import com.reservationapp.persistance.entity.MenuEntry;

@RestController
@RequestMapping("/api/menu")
public class MenuEntryController {

	@Autowired
	private MenuEntryService menuEntryService;

	@GetMapping("/getall")
	public ResponseEntity<?> getAllMenuEntries() {
		try {
			return new ResponseEntity<>(menuEntryService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/newMenuEntry")
	public ResponseEntity<?> newMenuEntry(@RequestBody MenuEntry menuEntry) {
		try {
			menuEntryService.createNewMenuEntry(menuEntry.getPrice(), menuEntry.getDescription(),
					menuEntry.getCantity(), menuEntry.getProductName(), menuEntry.getCategory(), menuEntry.getImage());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (MenuEntryAlreadyExistsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/editMenuEntry")
	public ResponseEntity<?> editMenuEntry(@RequestBody MenuEntry menuEntry) {
		try {
			menuEntryService.editMenuEntry(menuEntry.getPrice(), menuEntry.getDescription(),
					menuEntry.getCantity(), menuEntry.getProductName(), menuEntry.getCategory(), menuEntry.getImage());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (MenuEntryNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
