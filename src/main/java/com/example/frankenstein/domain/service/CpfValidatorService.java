package com.example.frankenstein.domain.service;

import org.springframework.stereotype.Service;

@Service
public class CpfValidatorService {

    public void validate(String cpf) {

        if (cpf == null || cpf.length() != 11) {
            throw new RuntimeException("CPF inválido");
        }
    }
}