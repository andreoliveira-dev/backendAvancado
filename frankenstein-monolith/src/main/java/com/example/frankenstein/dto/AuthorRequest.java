package com.example.frankenstein.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record AuthorRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "CPF is required")
        String cpf,
        @PositiveOrZero(message = "Annual income must be zero or positive")
        double annualIncome,
        @Valid
        List<BookRequest> books
) {
}
