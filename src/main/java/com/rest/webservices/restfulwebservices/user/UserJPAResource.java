package com.rest.webservices.restfulwebservices.user;

import com.rest.webservices.restfulwebservices.exceptions.UserNotFoundException;
import com.rest.webservices.restfulwebservices.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJPAResource {

	private UserDaoService service;
	private UserRepository repository;

	public UserJPAResource(UserDaoService service, UserRepository repository) {
		this.service = service;
		this.repository = repository;
	}
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return repository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public User retrieveUser(@PathVariable int id) {
		Optional<User> user = repository.findById(id);
		
		if (user.isEmpty()) {
			throw new UserNotFoundException("id:" + id);
		}
		
		return user.get();
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User createdUser = repository.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(createdUser.getId())
						.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		repository.deleteById(id);
	}

	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsForUser(@PathVariable int id) {
		Optional<User> user = repository.findById(id);

		if (user.isEmpty()) {
			throw new UserNotFoundException("id:" + id);
		}

		return user.get().getPosts();
	}
	
}
