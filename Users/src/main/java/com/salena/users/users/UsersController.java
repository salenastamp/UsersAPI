package com.salena.users.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
	
    @Autowired
    private UsersRespository userRepository;
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam(value="state", required=false) String state){
    	List<User> users;
    if (state != null) {
    users = userRepository.findByState(state);
    }
    else {
    	users = (List<User>) userRepository.findAll();
    }
    return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable(value="id")Long id){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
        	return new ResponseEntity<>(user, HttpStatus.OK);
        }

    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody @Valid User user, BindingResult bindingResult){
    	if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    	userRepository.save(user);
    	return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PutMapping("/users/{id}")
    public ResponseEntity<Void> changeUser(@PathVariable(value="id") Long id, @RequestBody User user, BindingResult bindingResult){
    	if (id == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    	userRepository.save(user);
    	return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> createUser(@PathVariable(value="id") Long id){
    	 Optional<User> user = userRepository.findById(id);
    	 if (!user.isPresent()) {
    		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	 } else {
    		 userRepository.deleteById(id);
    		 return new ResponseEntity<>(HttpStatus.OK);
    	 }
    }
}
