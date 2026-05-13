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
import com.example.frankenstein.repository.BookRepository;

@RestController
@RequestMapping("/api/v1/books")
public class BooksController {

    @Autowired
    private BookRepository repository;

    @GetMapping
    public ResponseEntity<List<Book>> listAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id){
        return ResponseEntity.ok(repository.findById(id).orElse(null));
    }   


    public ResponseEntity<Book> createBook(@RequestBody Book book){
        if (book.getTitle() == null  || !book.getTitle().isBlank()){
             throw new RuntimeException("titulo invalido!");
        }

        if(book.getAuthor() == null){
            throw new RuntimeException("livro sem autor!");
        }   
        Book savedBook = repository.save(book);

        URI uri = URI.create("/books/" + savedBook.getId());

        return ResponseEntity.created(uri).body(savedBook);
    }
    
}
