package com.reservationapp.business.service.exception;

import com.reservationapp.persistance.entity.MenuEntry;

public class MenuEntryNotFoundException extends Exception {
	public MenuEntryNotFoundException(String menuEntryProductName) {
		super("Menu entry not found: " + menuEntryProductName);
	}
}
