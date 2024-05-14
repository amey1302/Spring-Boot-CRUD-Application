package com.amaap.books.controller;

import com.amaap.books.entity.Book;
import com.amaap.books.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BookService bookService;

    @Test
    void shouldBeAbleToCreateABook() throws Exception {
        Book book = new Book("Clean Code4", "Uncle Bob", "OReally", 100);
        when(bookService.save(book)).thenReturn(book);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.asJsonString(book)))
                .andExpect(status().isOk());

    }

    @Test
    void shouldBeAbleToGetAllBooks() throws Exception {
        // arrange
        Book book1 = new Book("Clean Code", "Uncle Bob", "OReally", 100);
        Book book2 = new Book("Java", "Uncle Arc", "Oracle", 190);
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(book1);
        expectedBooks.add(book2);

        when(bookService.getAllBooks()).thenReturn(expectedBooks);

        // act & assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedBooks)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeAbleToGetBookByName() throws Exception {
        // arrange
        Book book1 = new Book("Clean Code", "Uncle Bob", "OReally", 100);

        when(bookService.getBookByName("Clean Code")).thenReturn(book1);

        // act & assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book?name=Clean Code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeAbleToGetBookByNameByPathParam() throws Exception {
        // arrange
        Book book1 = new Book("Clean Code", "Uncle Bob", "OReally", 100);

        when(bookService.getBookByName("Clean Code")).thenReturn(book1);

        // act & assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book/Clean Code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeAbleToUpdateBook() throws Exception {
        Book updatedBook = new Book("Clean Code", "New Author", "New Publication", 200);

        Book existingBook = new Book("Clean Code", "Existing Author", "Existing Publication", 100);
        when(bookService.getBookByName("Clean Code")).thenReturn(existingBook);

        when(bookService.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/v1/book/update/Clean Code")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.asJsonString(updatedBook))
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeAbleToDeleteBook() throws Exception {
        String bookName = "Clean Code";
        when(bookService.deleteBookByName(bookName)).thenReturn("Book with ID " + bookName + " deleted");
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/book/delete/{name}", bookName)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Book with ID " + bookName + " deleted"))
                .andDo(print());
    }

    @Test
    void shouldBeAbleToDeleteAllBooks() throws Exception {
        String expectedMessage = "Delete ALl Books";
        when(bookService.deleteAll()).thenReturn(expectedMessage);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/books/delete")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

}
