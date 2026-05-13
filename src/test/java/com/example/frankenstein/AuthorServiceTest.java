package com.example.frankenstein;

import com.example.frankenstein.domain.service.TaxCalculatorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorServiceTest {

    private final TaxCalculatorService service =
            new TaxCalculatorService();

    @Test
    void shouldApply15PercentTax() {

        Double result =
                service.applyTaxes(60000.0);

        assertEquals(51000.0, result);
    }

    @Test
    void shouldApply7PercentTax() {

        Double result =
                service.applyTaxes(40000.0);

        assertEquals(37200.0, result);
    }
}