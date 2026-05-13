package com.example.frankenstein.domain.dto;

import java.util.List;

public record AuthorResponseDTO(
        Long id,
        String name,
        String cpf,
        Double annualIncome,
        List<BookDTO> books
) {
}