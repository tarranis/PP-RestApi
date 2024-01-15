package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.configs.WebSecurityConfig;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User update(User user) {
        User desiredUser = userRepository.getById(user.getId());
        if (desiredUser == null) {
            System.out.println("Пользователь не найден");
            return null;
        }
        desiredUser.setId(user.getId());
        desiredUser.setName(user.getName());
        desiredUser.setLastName(user.getLastName());
        desiredUser.setAge(user.getAge());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            desiredUser.setPassword(WebSecurityConfig.passwordEncoder().encode(user.getPassword()));
        }
        desiredUser.setRoles(user.getRoles());
        return userRepository.save(desiredUser);
    }

    @Override
    @Transactional
    public boolean save(User user) {
        User desiredUser = userRepository.findByName(user.getName());
        if (desiredUser != null) {
            System.out.println("Такой пользователь уже существует");
            return false;
        }
        user.setRoles(user.getRoles());
        user.setPassword(WebSecurityConfig.passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> desiredUser = userRepository.findById(id);
        return desiredUser.orElse(new User());
    }

    @Override
    @Transactional(readOnly = true)
    public User findByName(String name) {
        Optional<User> desiredUser = Optional.ofNullable(userRepository.findByName(name));
        return desiredUser.orElse(null);
    }

}
