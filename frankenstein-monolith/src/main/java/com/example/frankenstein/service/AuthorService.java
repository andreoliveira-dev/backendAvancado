package com.example.frankenstein.service;

import com.example.frankenstein.dto.AuthorRequest;
import com.example.frankenstein.dto.AuthorResponse;
import com.example.frankenstein.dto.BookResponse;
import com.example.frankenstein.model.Author;
import com.example.frankenstein.model.Book;
import com.example.frankenstein.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorResponse> listAll() {
        // usei esse metodo do repository pra ja trazer os livros junto e evitar varios selects
        return authorRepository.findAllWithBooks()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public AuthorResponse save(AuthorRequest request) {
        validateCpf(request.cpf());

        Author author = new Author();
        author.setName(request.name());
        author.setCpf(request.cpf());
        // a regra do imposto saiu do controller e ficou aqui no service
        author.setAnnualIncome(applyTaxRule(request.annualIncome()));

        if (request.books() != null) {
            request.books().forEach(bookRequest -> {
                Book book = new Book();
                book.setTitle(bookRequest.title());
                book.setAuthor(author);
                author.getBooks().add(book);
            });
        }

        return toResponse(authorRepository.save(author));
    }

    public void delete(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new IllegalArgumentException("Author not found");
        }

        authorRepository.deleteById(id);
    }

    private void validateCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            throw new IllegalArgumentException("CPF Inválido!");
        }
    }

    private double applyTaxRule(double annualIncome) {
        if (annualIncome > 50000) {
            return annualIncome * 0.85;
        }

        return annualIncome * 0.93;
    }

    private AuthorResponse toResponse(Author author) {
        // preferi devolver dto pra nao expor a entidade direto na api
        return new AuthorResponse(
                author.getId(),
                author.getName(),
                author.getCpf(),
                author.getAnnualIncome(),
                author.getBooks().stream().map(this::toResponse).toList()
        );
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(book.getId(), book.getTitle());
    }
}
