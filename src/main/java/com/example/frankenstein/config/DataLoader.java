package com.example.frankenstein.config;

import com.example.frankenstein.model.Author;
import com.example.frankenstein.model.Book;
import com.example.frankenstein.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final AuthorRepository repository;

    @Override
    public void run(String... args) {

        for (int i = 1; i <= 50; i++) {

            Author author = new Author();

            author.setName("Author " + i);
            author.setCpf("12345678901");
            author.setAnnualIncome(70000.0);

            List<Book> books = new ArrayList<>();

            for (int j = 1; j <= 3; j++) {

                Book book = new Book();

                book.setTitle("Book " + j);
                book.setAuthor(author);

                books.add(book);
            }

            author.setBooks(books);

            repository.save(author);
        }
    }
}