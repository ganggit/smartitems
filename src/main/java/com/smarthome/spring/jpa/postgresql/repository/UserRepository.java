package com.smarthome.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarthome.spring.jpa.postgresql.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public List<User> getUserByUsername(String userName);
	public List<User> findByUsername(String username);
}
