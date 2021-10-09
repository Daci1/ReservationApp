package com.reservationapp.business.implementation;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reservationapp.business.service.UserService;
import com.reservationapp.persistance.entity.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private UserService userService = new UserServiceImpl();
	@Override
	public User loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		System.out.println(userEmail);
		return userService.findByEmail(userEmail).get();
	}
}
