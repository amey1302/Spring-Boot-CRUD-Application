package com.amaap.books.service;

import com.amaap.books.entity.Book;
import com.amaap.books.repository.BookRepository;
import com.amaap.books.service.exception.BookAlreadyExistException;
import com.amaap.books.service.exception.BookNotFoundException;
import com.amaap.books.service.exception.InvalidBookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public Book save(Book book) throws InvalidBookException, BookAlreadyExistException {
        if (isBookValid(book)) {
            if(!existsByName(book.getName())){
                return bookRepository.save(book);
            }else {
                throw new BookAlreadyExistException("Book With Same Name Exist");
            }

        }
        else {
            throw new InvalidBookException("Invalid Book Details..");
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public boolean existsByName(String name) {
        return bookRepository.existsById(name);
    }

    public boolean isBookValid(Book book) {
        return isNameValid(book.getName()) && isAuthorValid(book.getAuthor()) &&
                isPublicationValid(book.getPublication()) && isPriceValid(book.getPrice());
    }

    public boolean isPriceValid(double price) {
        return price > 0;
    }

    public boolean isPublicationValid(String publication) {
        return publication != null && !publication.isEmpty() && isAlphabetic(publication);
    }

    public boolean isAuthorValid(String author) {
        return author != null && !author.isEmpty() && isAlphabetic(author);
    }

    public boolean isNameValid(String name) {
        return name != null && !name.isEmpty() && isAlphabetic(name);
    }

    private boolean isAlphabetic(String value) {
        return value.matches("[a-zA-Z.\\s]*");
    }


    public Book getBookByName(String name) {
        Optional<Book> book = bookRepository.findById(name);
        return book.orElse(null);
    }

    public String updateBook(String name, Book updatedBook) throws BookNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(name);
        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException("Book not found with id: " + name);
        }

        Book existingBook = optionalBook.get();
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPublication(updatedBook.getPublication());
        existingBook.setPrice(updatedBook.getPrice());

        isBookValid(existingBook);

        Book savedBook = bookRepository.save(existingBook);
        return "Book updated: " + savedBook.getName();
    }
    public String deleteBookByName(String name) {
        Optional<Book> bookOptional = bookRepository.findById(name);
        if (bookOptional.isPresent()) {
            bookRepository.deleteById(name);
            return "Book with ID " + name + " deleted";
        } else {
            return "Book with ID " + name + " not found";
        }
    }

    public String deleteAll() {
        long countBeforeDelete = bookRepository.count();
        bookRepository.deleteAll();
        long countAfterDelete = bookRepository.count();
        long totalDeleted = countBeforeDelete - countAfterDelete;
        return totalDeleted + " books deleted";
    }
}
