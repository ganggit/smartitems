package com.smarthome.spring.jpa.postgresql.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarthome.spring.jpa.postgresql.model.User;
import com.smarthome.spring.jpa.postgresql.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	UserRepository userRepository;

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
		Optional<User> userData = userRepository.findById(id);

		if (userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		try {
			List<User> users = userRepository.findByUsername(user.getUsername());
			if(users.size()>0) {
				return new ResponseEntity<>(user, HttpStatus.CONFLICT); 
			}
			User _user = userRepository
					.save(new User(user.getFullName(), user.getAddress(), user.getPhone(), user.getUsername(), user.getPassword(), user.getACL(), null));
			return new ResponseEntity<>(_user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public List<User> getUserByUsername(String userName) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.select(root).where(cb.equal(root.get("username"), userName));  //here you pass a class field, not a table column (in this example they are called the same)

        Query<User> query = session.createQuery(cr);
        query.setMaxResults(1);
        List<User> result = query.getResultList();
        session.close();
        return result;
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		Optional<User> userData = userRepository.findById(id);

		if (userData.isPresent()) {
			User _user = userData.get();
			_user.setFullName(user.getFullName());
			_user.setPhone(user.getPhone());
			_user.setImage(user.getImage());
			_user.setAddress(user.getAddress());
			_user.setPassword(user.getPassword());
			return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
		try {
			userRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/user")
	public ResponseEntity<HttpStatus> deleteAllUsers() {
		try {
			// TO DO: add permission check
			userRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
