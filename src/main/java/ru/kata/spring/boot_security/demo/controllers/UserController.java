package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping()
    public String printUser(Model model, Principal principal) {
        User currentUser = userServiceImpl.findByName(principal.getName());
        if (currentUser != null) {
            model.addAttribute("currentuser", currentUser);
        } else {
            System.out.println("Пользователь не найден");
        }
        return "user/user";
    }
}
