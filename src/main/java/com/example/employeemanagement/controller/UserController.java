package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.request.UserRequest;
import com.example.employeemanagement.dto.response.UserResponse;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.service.UserService;
import com.example.employeemanagement.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse>  userResponseList = userService.findAll();
        return ResponseEntity.ok(userResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable @Positive(message = "Id must be greater than 0") Long id){
        UserResponse userResponse = userService.findById(id);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody @Valid UserRequest userRequest){
        UserResponse userCreate = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreate);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable @Positive(message = "Id must be greater than 0") Long id, @RequestBody @Valid UserRequest userRequest){
        UserResponse userUpdate = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(userUpdate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive(message = "Id must be greater than 0") Long id){
        userService.deleteUser(id);
    }
}
