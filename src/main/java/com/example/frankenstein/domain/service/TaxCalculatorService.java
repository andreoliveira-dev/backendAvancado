package com.example.frankenstein.domain.service;

import org.springframework.stereotype.Service;

@Service
public class TaxCalculatorService {

    public Double applyTaxes(Double income) {

        if (income > 50000) {
            return income * 0.85;
        }

        return income * 0.93;
    }
}