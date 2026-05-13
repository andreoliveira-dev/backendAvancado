package com.example.frankenstein.domain.dto;

import java.util.List;

public record AuthorRequestDTO(
        String name,
        String cpf,
        Double annualIncome,
        List<BookDTO> books
) {
}