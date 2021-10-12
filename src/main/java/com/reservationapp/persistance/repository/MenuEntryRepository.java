package com.reservationapp.persistance.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.reservationapp.persistance.entity.MenuEntry;

public interface MenuEntryRepository extends CrudRepository<MenuEntry, Integer> {
	public Set<MenuEntry> findByCategory(String Category);

	public Optional<MenuEntry> findByProductName(String productName);
}
