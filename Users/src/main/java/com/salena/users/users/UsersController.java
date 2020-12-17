package com.salena.users.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
    public List<User> getUsers(@RequestParam(value="state", required=false) String state){
    if (state != null) {
    return (List<User>) userRepository.findByState(state);
    }
    return (List<User>) userRepository.findAll();
    }
    
    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable(value="id") Long id){
    return userRepository.findById(id);
    }

    @PostMapping("/users")
    public void createUser(@RequestBody User user){
    userRepository.save(user);
    }
    
    @PutMapping("/users/{id}")
    public void changeUser(@PathVariable(value="id") Long id, @RequestBody User user){
    userRepository.save(user);
    }
    
    @DeleteMapping("/users/{id}")
    public void createUser(@PathVariable(value="id") Long id){
    userRepository.deleteById(id);
    }


}
