package com.reservationapp.business.implementation;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservationapp.business.service.MenuEntryService;
import com.reservationapp.business.service.exception.MenuEntryAlreadyExistsException;
import com.reservationapp.business.service.exception.MenuEntryNotFoundException;
import com.reservationapp.persistance.entity.MenuEntry;
import com.reservationapp.persistance.repository.MenuEntryRepository;

@Service
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

	@Override
	public Optional<MenuEntry> getMenuEntryByProductName(String productName) {
		return menuEntryRepo.findByProductName(productName);
	}

	@Override
	public void editMenuEntry(Double price, String description, Double cantity, String productName, String category) throws MenuEntryNotFoundException {
		Optional<MenuEntry> editedMenuEntry = menuEntryRepo.findByProductName(productName);
		if(editedMenuEntry.isPresent()) {
			MenuEntry menuEntryAfterEdit = editedMenuEntry.get();
			menuEntryAfterEdit.setPrice(price);
			menuEntryAfterEdit.setDescription(description);
			menuEntryAfterEdit.setQuantity(cantity);
			menuEntryAfterEdit.setProductName(productName);
			menuEntryAfterEdit.setCategory(category);
		}else {
			throw new MenuEntryNotFoundException(productName);
		}
	}
}
