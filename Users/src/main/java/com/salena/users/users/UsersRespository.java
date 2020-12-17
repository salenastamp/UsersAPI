package com.salena.users.users;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRespository extends CrudRepository<User, Long>{
	List<User> findByState(String state);
}