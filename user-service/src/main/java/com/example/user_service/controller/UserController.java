package com.example.user_service.controller;

import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repo;
    public UserController(UserRepository repo) { this.repo = repo; }

    @GetMapping
    public List<User> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User create(@RequestBody User u) { return repo.save(u); }
}
