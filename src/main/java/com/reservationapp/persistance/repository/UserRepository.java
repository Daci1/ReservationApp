package com.reservationapp.persistance.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.reservationapp.persistance.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	
	public Optional<User> findByFirstName(String firstName);

	public Optional<User> findByEmail(String email);
}
