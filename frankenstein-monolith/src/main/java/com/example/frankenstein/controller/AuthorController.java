package com.example.frankenstein.controller;

import com.example.frankenstein.model.Author;
import com.example.frankenstein.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository repository;

    @GetMapping
    public ResponseEntity<List<Author>> listAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable Long id){
        return  ResponseEntity.ok(repository.findById(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<Author> save(@RequestBody Author author) {
        if (author.getCpf() == null || author.getCpf().length() != 11) {
            throw new RuntimeException("CPF Inválido!");
        }
        if (author.getAnnualIncome() > 50000) {
            author.setAnnualIncome(author.getAnnualIncome() * 0.85);
        } else {
            author.setAnnualIncome(author.getAnnualIncome() * 0.93);
        }

        return ResponseEntity.ok(repository.save(author));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
