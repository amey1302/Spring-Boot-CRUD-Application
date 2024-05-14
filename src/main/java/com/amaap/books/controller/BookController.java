package com.amaap.books.controller;

import com.amaap.books.entity.Book;
import com.amaap.books.service.BookService;
import com.amaap.books.service.exception.BookAlreadyExistException;
import com.amaap.books.service.exception.BookNotFoundException;
import com.amaap.books.service.exception.InvalidBookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<?> create(@RequestBody Book book) {
        try {
            Book savedBook = bookService.save(book);
            return ResponseEntity.ok(savedBook);
        } catch (InvalidBookException e) {
            return ResponseEntity.badRequest().body("Invalid Book Details");
        } catch (BookAlreadyExistException e) {
            return ResponseEntity.badRequest().body("Book Already Exists");
        }
    }

    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if(books.isEmpty()){
            return ResponseEntity.ok().body("All books are deleted");
        }else {
            return ResponseEntity.ok(books);
        }
    }

    @GetMapping("/book")
    public ResponseEntity<?> getBookByName(@RequestParam(required = true) String name){
        Book book = bookService.getBookByName(name);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.badRequest().body("Book Not Found");
        }
    }

    @GetMapping("/book/{name}")
    public ResponseEntity<?> getBookByNameUsingPathVariable(@PathVariable(required = true) String name){
        Book book = bookService.getBookByName(name);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.badRequest().body("Book Not Found");
        }
    }

    @PutMapping("/book/update/{name}")
    public ResponseEntity<?> updateBook(@RequestBody Book updatedBook, @PathVariable String name) {
        try {
            String result = bookService.updateBook(name, updatedBook);
            return ResponseEntity.ok(result);
        } catch (BookNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/book/delete/{name}")
    public String deleteBook(@PathVariable String name){
        return bookService.deleteBookByName(name);
    }
    @DeleteMapping("/books/delete")
    public ResponseEntity<?> deleteAllBooks() {
        try {
            bookService.deleteAll();
            return ResponseEntity.ok().body("Deleted All Books");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
