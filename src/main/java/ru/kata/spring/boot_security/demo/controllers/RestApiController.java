package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {

    private final UserServiceImpl userServiceImpl;
    private final RoleRepository roleRepository;

    public RestApiController(UserServiceImpl userServiceImpl, RoleRepository roleRepository) {
        this.userServiceImpl = userServiceImpl;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public ResponseEntity<List<User>> printAllUsers() {
        List<User> allUsers = userServiceImpl.getAll();
        if (allUsers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> printUser(@PathVariable Long id) {
        User user = userServiceImpl.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> printAllRoles() {
        List<Role> allRoles = roleRepository.findAll();
        if (allRoles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allRoles);
    }

    @PostMapping()
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        userServiceImpl.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        userServiceImpl.update(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        User user = userServiceImpl.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.getRoles().clear();
        userServiceImpl.delete(id);
        return ResponseEntity.noContent().build();
    }

}

