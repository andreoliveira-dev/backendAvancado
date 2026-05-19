package com.example.frankenstein.dto;

import jakarta.validation.constraints.NotBlank;

public record BookRequest(
        @NotBlank(message = "Book title is required")
        String title
) {
}
