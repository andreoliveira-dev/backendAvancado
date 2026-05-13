package com.example.frankenstein.controller;

import com.example.frankenstein.config.SecurityConfig.JwtAuthFilter;
import com.example.frankenstein.model.Author;
import com.example.frankenstein.repository.AuthorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AuthorController {

    private final AuthorRepository repository;
    private final JwtAuthFilter jwtAuthFilter;

    public AuthorController(AuthorRepository repository, JwtAuthFilter jwtAuthFilter) {
        this.repository = repository;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if ("admin".equals(username) && "12345".equals(password)) {
            String token = jwtAuthFilter.generateToken(username, "ADMIN");
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }

    @GetMapping("/api/v1/authors")
    public List<Author> listAll() {
        return repository.findAllWithBooks();
    }

    @PostMapping("/api/v1/authors")
    public ResponseEntity<Author> save(@RequestBody Author author) {
        validateCpf(author.getCpf());
        applyTax(author);
        return ResponseEntity.status(201).body(repository.save(author));
    }

    @DeleteMapping("/api/v1/authors/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void validateCpf(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido! Deve conter 11 dígitos numéricos.");
        }
    }

    private void applyTax(Author author) {
        if (author.getAnnualIncome() == null) return;
        double factor = author.getAnnualIncome() > 50000 ? 0.85 : 0.93;
        author.setAnnualIncome(author.getAnnualIncome() * factor);
    }
}