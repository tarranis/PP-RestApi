package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userServiceImpl;

    private final RoleRepository roleRepository;

    public AdminController(UserServiceImpl userServiceImpl, RoleRepository roleRepository) {
        this.userServiceImpl = userServiceImpl;
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    public String printAllUser(Model model, Principal principal) {
        model.addAttribute("users", userServiceImpl.getAll());
        model.addAttribute("currentUser", userServiceImpl.findByName(principal.getName()));
        model.addAttribute("allRoles", roleRepository.findAll());
        return "admin/allUsers";
    }

    @GetMapping("/currentAdminUser")
    public String printCurrentUser(Model model, Principal principal) {
        model.addAttribute("currentAdminUser", userServiceImpl.findByName(principal.getName()));
        return "admin/currentAdminUser";
    }

}
