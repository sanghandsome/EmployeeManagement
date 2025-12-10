package com.example.employeemanagement.repository;


import com.example.employeemanagement.model.RedisToken;
import org.springframework.data.repository.CrudRepository;

public interface RedisTokenRepository extends CrudRepository<RedisToken, String> {

}
