package com.example.Midaxus.controller;

import com.example.Midaxus.model.dtos.UserDTO;
import com.example.Midaxus.services.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUser<UserDTO, String> userRepos;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {

        UserDTO created = userRepos.createUser(dto);


        return ResponseEntity
                .created(URI.create("/api/users")).body(created);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        userRepos.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



}
