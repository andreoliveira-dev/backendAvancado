package com.example.frankenstein.repository;

import com.example.frankenstein.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    // fiz com join fetch pra resolver o problema de n+1 na listagem
    @Query("select distinct a from Author a left join fetch a.books")
    List<Author> findAllWithBooks();
}
