package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User update(User user);

    boolean save(User user);

    void delete(Long id);

    User findById(Long id);

    User findByName(String name);

}
