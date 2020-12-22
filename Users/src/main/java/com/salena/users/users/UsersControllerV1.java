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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@Api(value="users", description="Operations pertaining to Users")
public class UsersControllerV1 {
	
    @Autowired
    private UsersRespository userRepository;
    
    @GetMapping("v1//users")
    @ApiOperation(value = "Get all users", response = User.class, 
    responseContainer = "List")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "Successfully retrieved users list"),
   	})
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
    
    @GetMapping("/v1/users/{id}")
    @ApiOperation(value = "Get a user by ID", response = User.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "Successfully retrieved user"),
    		@ApiResponse(code = 404, message = "User not found")
   	})
    public ResponseEntity<Optional<User>> getUserById(@PathVariable(value="id")Long id){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
        	return new ResponseEntity<>(user, HttpStatus.OK);
        }

    }

    @PostMapping("/v1/users")
    @ApiOperation(value = "Post a new user", response = User.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 201, message = "Successfully created user"),
    		@ApiResponse(code = 400, message = "Bad request")
   	})	
    public ResponseEntity<Void> createUser(@RequestBody @Valid User user, BindingResult bindingResult){
    	if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    	userRepository.save(user);
    	return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PutMapping("/v1/users/{id}")
    @ApiOperation(value = "Put a change to a user by id", response = User.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 201, message = "Successfully updated user"),
    		@ApiResponse(code = 400, message = "Bad request"),
    		@ApiResponse(code = 404, message = "User not found")
   	})
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
    
    @DeleteMapping("/v1/users/{id}")
    @ApiOperation(value = "Delete a user by ID", response = User.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "Successfully deleted user"),
    		@ApiResponse(code = 404, message = "User not found")
   	})
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
