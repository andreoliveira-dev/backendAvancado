package com.example.frankenstein.service;

import com.example.frankenstein.dto.AuthorRequest;
import com.example.frankenstein.dto.AuthorResponse;
import com.example.frankenstein.dto.BookRequest;
import com.example.frankenstein.model.Author;
import com.example.frankenstein.model.Book;
import com.example.frankenstein.repository.AuthorRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthorServiceTest {

    @Test
    void shouldApplyTaxRuleBeforeSavingAuthor() {
        AuthorRepository repository = mock(AuthorRepository.class);
        AuthorService service = new AuthorService(repository);

        when(repository.save(any(Author.class))).thenAnswer(invocation -> {
            Author author = invocation.getArgument(0);
            author.setId(1L);
            Book savedBook = new Book();
            savedBook.setId(1L);
            savedBook.setTitle("Frankenstein");
            savedBook.setAuthor(author);
            author.getBooks().clear();
            author.getBooks().add(savedBook);
            return author;
        });

        AuthorResponse response = service.save(
                new AuthorRequest("Mary Shelley", "12345678901", 60000, List.of(new BookRequest("Frankenstein")))
        );

        assertThat(response.annualIncome()).isEqualTo(51000);
        assertThat(response.books()).hasSize(1);
    }

    @Test
    void shouldRejectInvalidCpf() {
        AuthorRepository repository = mock(AuthorRepository.class);
        AuthorService service = new AuthorService(repository);

        assertThatThrownBy(() -> service.save(new AuthorRequest("Mary Shelley", "123", 1000, List.of())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("CPF Inválido!");
    }
}
