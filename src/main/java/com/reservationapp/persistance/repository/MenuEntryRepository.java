package com.reservationapp.persistance.repository;

import org.springframework.data.repository.CrudRepository;

import com.reservationapp.persistance.entity.MenuEntry;

public interface MenuEntryRepository extends CrudRepository<MenuEntry, Integer>{

}
