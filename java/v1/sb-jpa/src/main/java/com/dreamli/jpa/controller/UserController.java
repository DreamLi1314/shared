package com.dreamli.jpa.controller;

import com.dreamli.jpa.domain.User;
import com.dreamli.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Integer id) {
        User user = repository.findById(id).orElse(null);

        return user;
    }

    @GetMapping("/user")
    public User adduer(User user) {
        User save = repository.save(user);

        return save;
    }

}
