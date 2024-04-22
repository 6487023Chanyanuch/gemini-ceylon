package com.example.demo.controller;

import com.example.demo.model.Astronomer;
import com.example.demo.model.ScienceObserver;
import com.example.demo.model.User;
import com.example.demo.repository.ScienceObserverRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.AstronomerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AstronomerRepository astronomerRepository;
    @Autowired
    private ScienceObserverRepository scienceObserverRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/astronomer")
    public Astronomer addAstronomerID(@RequestBody Astronomer astronomer) { return astronomerRepository.save(astronomer); }

    @PostMapping("/sciobserver")
    public ScienceObserver addSciobserverID(@RequestBody ScienceObserver scienceObserver) { return scienceObserverRepository.save(scienceObserver); }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userRepository.findById(id).map(u -> {
            u.setFirstname(user.getFirstname());
            u.setLastname(user.getLastname());
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());
            u.setPhone(user.getPhone());
            return userRepository.save(u);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/userId")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        String userName = (String) session.getAttribute("userName");

        if (userId != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("userName", userName); // เพิ่มชื่อผู้ใช้งานเข้าไปใน response
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/astro/{userId}")
    public String showAstroPage ( @PathVariable Long userId, Model model ) {
        // Add logic here to retrieve data for the Astronomer page if needed
        return "astro";
    }

    @GetMapping("/sci/{userId}")
    public String showSciObPage ( @PathVariable Long userId, Model model ) {
        // Add logic here to retrieve data for the Astronomer page if needed
        return "sci";
    }

}