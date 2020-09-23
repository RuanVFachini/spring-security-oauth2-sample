package br.com.ruanfachini.blog.controllers;

import br.com.ruanfachini.blog.domains.User;
import br.com.ruanfachini.blog.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioRepository.saveAndFlush(user));
    }
}
