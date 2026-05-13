package com.example.frankenstein.controller;

import java.net.URI;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.frankenstein.model.Book;
import com.example.frankenstein.model.BookRequestDTO;
import com.example.frankenstein.repository.AuthorRepository;
import com.example.frankenstein.repository.BookRepository;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/books")
public class BooksController {

    @Autowired
    private BookRepository repository;

    @Autowired
    private AuthorRepository    authorRepository;

    @GetMapping
    public ResponseEntity<List<Book>> listAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id){
        return ResponseEntity.ok(repository.findById(id).orElse(null));
    }   


    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookRequestDTO book){

        if (book.title() == null  || book.title().isBlank()){
             throw new RuntimeException("titulo invalido!");
        }

        var author = authorRepository.findById((long) book.author()).orElseThrow(() -> new RuntimeException("Autor não encontrado!"));

        Book newBook = new Book();
        newBook.setTitle(book.title());
        newBook.setAuthor(author);

        Book savedBook = repository.save(newBook);

        URI uri = URI.create("/books/" + savedBook.getId());

        return ResponseEntity.created(uri).body(savedBook);
    }
    
}
