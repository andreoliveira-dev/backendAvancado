package com.example.frankenstein.domain.service;

import com.example.frankenstein.domain.dto.AuthorRequestDTO;
import com.example.frankenstein.domain.dto.AuthorResponseDTO;
import com.example.frankenstein.domain.dto.BookDTO;
import com.example.frankenstein.model.Author;
import com.example.frankenstein.model.Book;
import com.example.frankenstein.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;
    private final CpfValidatorService cpfValidatorService;
    private final TaxCalculatorService taxCalculatorService;

    public List<AuthorResponseDTO> findAll() {

        return repository.findAllWithBooks()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public AuthorResponseDTO save(AuthorRequestDTO dto) {

        cpfValidatorService.validate(dto.cpf());

        Author author = new Author();

        author.setName(dto.name());
        author.setCpf(dto.cpf());

        Double incomeWithTaxes =
                taxCalculatorService.applyTaxes(dto.annualIncome());

        author.setAnnualIncome(incomeWithTaxes);

        List<Book> books = dto.books()
                .stream()
                .map(bookDTO -> {

                    Book book = new Book();

                    book.setTitle(bookDTO.title());
                    book.setAuthor(author);

                    return book;
                })
                .toList();

        author.setBooks(books);

        Author saved = repository.save(author);

        return toResponseDTO(saved);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private AuthorResponseDTO toResponseDTO(Author author) {

        List<BookDTO> books =
                author.getBooks()
                        .stream()
                        .map(book -> new BookDTO(
                                book.getId(),
                                book.getTitle()
                        ))
                        .toList();

        return new AuthorResponseDTO(
                author.getId(),
                author.getName(),
                author.getCpf(),
                author.getAnnualIncome(),
                books
        );
    }
}