package com.reservationapp.business.service.exception;

import com.reservationapp.persistance.entity.MenuEntry;

public class MenuEntryAlreadyExistsException extends Exception {
	public MenuEntryAlreadyExistsException(MenuEntry menuEntry) {
		super("This menu entry already exists: " + menuEntry.toString());
	}
}
