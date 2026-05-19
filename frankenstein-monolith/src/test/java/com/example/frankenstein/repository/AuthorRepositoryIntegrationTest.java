package com.example.frankenstein.repository;

import com.example.frankenstein.model.Author;
import com.example.frankenstein.model.Book;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.jpa.properties.hibernate.generate_statistics=true"
})
class AuthorRepositoryIntegrationTest {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        IntStream.rangeClosed(1, 50).forEach(index -> repository.save(buildAuthor(index)));
    }

    @Test
    void shouldLoadAuthorsAndBooksWithoutNPlusOne() {
        Statistics statistics = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
        statistics.clear();

        repository.findAllWithBooks();

        assertThat(statistics.getPrepareStatementCount()).isEqualTo(1);
    }

    private Author buildAuthor(int index) {
        Author author = new Author();
        author.setName("Author " + index);
        author.setCpf(String.format("%011d", index));
        author.setAnnualIncome(20000.0 + index);

        IntStream.rangeClosed(1, 2).forEach(bookIndex -> {
            Book book = new Book();
            book.setTitle("Book %d-%d".formatted(index, bookIndex));
            book.setAuthor(author);
            author.getBooks().add(book);
        });

        return author;
    }
}
