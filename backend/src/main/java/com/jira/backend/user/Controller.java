package com.jira.backend.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    private final UserRepository userRepository;

    Controller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/test")
    public List<User> testDB() {
        List<User> users = userRepository.findAll();
        System.out.println(users);
        return users;
    }
}
