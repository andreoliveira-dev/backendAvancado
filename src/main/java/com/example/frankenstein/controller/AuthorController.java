package com.example.frankenstein.controller;

import com.example.frankenstein.domain.dto.AuthorRequestDTO;
import com.example.frankenstein.domain.dto.AuthorResponseDTO;
import com.example.frankenstein.domain.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @GetMapping
    public List<AuthorResponseDTO> listAll() {
        return service.findAll();
    }

    @PostMapping
    public AuthorResponseDTO save(
            @RequestBody AuthorRequestDTO dto
    ) {
        return service.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}