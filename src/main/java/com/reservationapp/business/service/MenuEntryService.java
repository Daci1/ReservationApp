package com.reservationapp.business.service;

import java.util.Optional;
import java.util.Set;

import com.reservationapp.business.service.exception.MenuEntryAlreadyExistsException;
import com.reservationapp.business.service.exception.MenuEntryNotFoundException;
import com.reservationapp.persistance.entity.MenuEntry;

public interface MenuEntryService {

	public Set<MenuEntry> getAll();

	public void createNewMenuEntry(Double price, String description, Double cantity, String productName,
			String category) throws MenuEntryAlreadyExistsException;

	public void save(MenuEntry menuEntry);

	public Set<MenuEntry> getMenuEntryByCategory(String category);
	
	public Optional<MenuEntry> getMenuEntryByProductName(String productName);
	
	public void editMenuEntry(Double price, String description, Double cantity, String productName,
			String category, String oldMenuEntryName) throws MenuEntryNotFoundException;

	public void deleteMenuEntry(MenuEntry menuEntry);
}
