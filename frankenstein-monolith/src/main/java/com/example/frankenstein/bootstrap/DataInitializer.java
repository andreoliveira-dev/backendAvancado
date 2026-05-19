package com.example.frankenstein.bootstrap;

import com.example.frankenstein.model.Author;
import com.example.frankenstein.model.Book;
import com.example.frankenstein.model.UserAccount;
import com.example.frankenstein.repository.AuthorRepository;
import com.example.frankenstein.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminUsername;
    private final String adminPassword;

    public DataInitializer(
            AuthorRepository authorRepository,
            UserAccountRepository userAccountRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.security.seed.admin-username}") String adminUsername,
            @Value("${app.security.seed.admin-password}") String adminPassword
    ) {
        this.authorRepository = authorRepository;
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) {
        seedAuthors();
        seedAdminUser();
    }

    private void seedAuthors() {
        if (authorRepository.count() > 0) {
            return;
        }

        // o readme pedia uma massa com varios autores, entao deixei 50 aqui
        List<Author> authors = IntStream.rangeClosed(1, 50)
                .mapToObj(this::buildAuthor)
                .toList();

        authorRepository.saveAll(authors);
    }

    private Author buildAuthor(int index) {
        Author author = new Author();
        author.setName("Author " + index);
        author.setCpf(String.format("%011d", index));
        author.setAnnualIncome(30000.0 + (index * 1000));

        IntStream.rangeClosed(1, 3)
                .mapToObj(bookIndex -> buildBook(index, bookIndex, author))
                .forEach(book -> author.getBooks().add(book));

        return author;
    }

    private Book buildBook(int authorIndex, int bookIndex, Author author) {
        Book book = new Book();
        book.setTitle("Book %d-%d".formatted(authorIndex, bookIndex));
        book.setAuthor(author);
        return book;
    }

    private void seedAdminUser() {
        // so cria o admin se usuario e senha vierem por variavel, pra nao deixar fixo no codigo
        if (!StringUtils.hasText(adminUsername) || !StringUtils.hasText(adminPassword)) {
            return;
        }

        if (userAccountRepository.existsById(adminUsername)) {
            return;
        }

        UserAccount user = new UserAccount();
        user.setUsername(adminUsername);
        user.setPassword(passwordEncoder.encode(adminPassword));
        user.setRole("ADMIN");
        userAccountRepository.save(user);
    }
}
