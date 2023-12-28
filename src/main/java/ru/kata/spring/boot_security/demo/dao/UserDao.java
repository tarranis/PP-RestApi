package ru.kata.spring.boot_security.demo.dao;





import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    User update(User user);

    void save(User user);

    void delete(User user);

    User findById(Long id);

}
