package com.reservationapp.business.implementation;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.reservationapp.business.service.MenuEntryService;
import com.reservationapp.business.service.exception.MenuEntryAlreadyExistsException;
import com.reservationapp.persistance.entity.MenuEntry;
import com.reservationapp.persistance.repository.MenuEntryRepository;

public class MenuEntryServiceImpl implements MenuEntryService {

	@Autowired
	private MenuEntryRepository menuEntryRepo;

	@Override
	public Set<MenuEntry> getAll() {
		Set<MenuEntry> allMenuEntries = new HashSet<>();
		menuEntryRepo.findAll().forEach(menuEntry -> allMenuEntries.add(menuEntry));
		return allMenuEntries;
	}

	@Override
	public void save(MenuEntry menuEntry) {
		menuEntryRepo.save(menuEntry);
	}

	@Override
	public Set<MenuEntry> getMenuEntryByCategory(String category) {
		return menuEntryRepo.findByCategory(category);
	}

	@Override
	public void createNewMenuEntry(Double price, String description, Double cantity, String productName,
			String category) throws MenuEntryAlreadyExistsException {
		Optional<MenuEntry> alreadyExistingMenuEntry = menuEntryRepo.findByProductName(productName);
		if (alreadyExistingMenuEntry.isPresent()) {
			throw new MenuEntryAlreadyExistsException(alreadyExistingMenuEntry.get());
		} else {
			MenuEntry newMenuEntry = new MenuEntry(price, description, cantity, productName, category);
			menuEntryRepo.save(newMenuEntry);
		}
	}
}
