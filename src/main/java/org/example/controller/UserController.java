package org.example.controller;

import org.example.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // Dummy in-memory list (acting as our temporary database)
    private final List<User> users = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(3);

    public UserController() {
        // Pre-populate with dummy data
        users.add(new User(1L, "Alice Johnson", "alice@example.com"));
        users.add(new User(2L, "Bob Smith", "bob@example.com"));
        users.add(new User(3L, "Charlie Brown", "charlie@example.com"));
    }

    // 1. GET ALL: GET /api/users
    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

    // 2. GET ONE: GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. CREATE: POST /api/users
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        newUser.setId(idCounter.incrementAndGet());
        users.add(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    // 4. UPDATE: PUT /api/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedData) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                user.setName(updatedData.getName());
                user.setEmail(updatedData.getEmail());
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // 5. DELETE: DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean removed = users.removeIf(u -> u.getId().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }
}
