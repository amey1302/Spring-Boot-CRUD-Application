package com.amaap.books.service;

import com.amaap.books.entity.Book;
import com.amaap.books.repository.BookRepository;
import com.amaap.books.service.exception.BookAlreadyExistException;
import com.amaap.books.service.exception.BookNotFoundException;
import com.amaap.books.service.exception.InvalidBookException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceTest {
    @Autowired
    BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldBeAbleToSaveBookInRepository() throws InvalidBookException, BookAlreadyExistException {
        // arrange
        Book book = new Book("Clean Code", "Author", "Publisher", 100.0);
        when(bookRepository.save(book)).thenReturn(book);

        // act
        Book savedBook = bookService.save(book);

        // assert
        assertNotNull(savedBook);
        assertEquals("Clean Code", savedBook.getName());
        assertEquals("Author", savedBook.getAuthor());
        assertEquals("Publisher", savedBook.getPublication());
        assertEquals(100.0, savedBook.getPrice());
    }

    @Test
    void shouldBeAbleToGetAllBooksFromRepository() {
        // arrange
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book("Book 1", "Author 1", "Publisher 1", 50.0));
        expectedBooks.add(new Book("Book 2", "Author 2", "Publisher 2", 60.0));
        when(bookRepository.findAll()).thenReturn(expectedBooks);

        // act
        List<Book> actualBooks = bookService.getAllBooks();

        // assert
        assertEquals(expectedBooks.size(), actualBooks.size());
        for (int i = 0; i < expectedBooks.size(); i++) {
            Book expectedBook = expectedBooks.get(i);
            Book actualBook = actualBooks.get(i);
            assertEquals(expectedBook.getName(), actualBook.getName());
            assertEquals(expectedBook.getAuthor(), actualBook.getAuthor());
            assertEquals(expectedBook.getPublication(), actualBook.getPublication());
            assertEquals(expectedBook.getPrice(), actualBook.getPrice());
        }
    }

    @Test
    void shouldBeAbleToCheckIfBookExistsByNameInRepository() {
        // arrange
        when(bookRepository.existsById("Clean Code")).thenReturn(true);
        when(bookRepository.existsById("Nonexistent Book")).thenReturn(false);

        // act
        boolean existsCleanCode = bookService.existsByName("Clean Code");
        boolean existsNonexistent = bookService.existsByName("Nonexistent Book");

        // assert
        assertTrue(existsCleanCode);
        assertFalse(existsNonexistent);
    }

    @Test
    void shouldBeAbleToValidateBook() {
        // arrange
        Book validBook = new Book("Clean Code", "Author", "Publisher", 100.0);
        Book invalidBook = new Book(null, "Author", "Publisher", 100.0);

        // act & assert
        assertTrue(bookService.isBookValid(validBook));
        assertFalse(bookService.isBookValid(invalidBook));
    }

    @Test
    void shouldBeAbleToGetBookByName() {
        // arrange
        Book book = new Book("Clean Code", "Author", "Publisher", 100.0);
        when(bookRepository.findById("Clean Code")).thenReturn(Optional.of(book));

        // assert
        assertEquals(book, bookService.getBookByName("Clean Code"));
    }
    @Test
    void shouldUpdateBook() throws BookNotFoundException {
        // arrange
        String name = "Clean Code";
        Book existingBook = new Book("Clean Code", "Author", "Publication", 100);
        Book updatedBook = new Book("Clean Code", "New Author", "New Publication", 200);

        when(bookRepository.findById(name)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(updatedBook);

        // act
        String result = bookService.updateBook(name, updatedBook);

        // assert
        assertEquals("Book updated: Clean Code", result);
        assertEquals("New Author", existingBook.getAuthor());
        assertEquals("New Publication", existingBook.getPublication());
        assertEquals(200, existingBook.getPrice());
    }
    @Test
    void shouldDeleteExistingBook() {
        // arrange
        String name = "Clean Code";
        Book book = new Book(name, "Author", "Publication", 100);
        when(bookRepository.findById(name)).thenReturn(Optional.of(book));

        // act
        String result = bookService.deleteBookByName(name);

        // assert
        assertEquals("Book with ID Clean Code deleted", result);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingBook() {
        // arrange
        String name = "Non Existing Book";
        when(bookRepository.findById(name)).thenReturn(Optional.empty());

        // act
        String result = bookService.deleteBookByName(name);

        // assert
        assertEquals("Book with ID Non Existing Book not found", result);
    }

    @Test
    void shouldDeleteAllBooks() {
        // arrange
        when(bookRepository.count()).thenReturn(5L, 0L);

        // act
        String result = bookService.deleteAll();

        // assert
        assertEquals("5 books deleted", result);
    }
}
