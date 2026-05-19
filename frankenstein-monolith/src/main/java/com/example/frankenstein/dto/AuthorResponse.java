package com.example.frankenstein.dto;

import java.util.List;

public record AuthorResponse(
        Long id,
        String name,
        String cpf,
        double annualIncome,
        List<BookResponse> books
) {
}
