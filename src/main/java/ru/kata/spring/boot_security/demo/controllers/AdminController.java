package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    public String printAllUser(Model model, Principal principal) {
        model.addAttribute("users", userService.getAll());
        model.addAttribute("currentUser", userService.findByName(principal.getName()));
        model.addAttribute("allRoles", roleRepository.findAll());
        return "admin/allUsers";
    }

    @GetMapping("/currentAdminUser")
    public String printCurrentUser(Model model, Principal principal) {
        model.addAttribute("currentAdminUser", userService.findByName(principal.getName()));
        return "admin/currentAdminUser";
    }

}
