package com.amaap.books.repository;

import com.amaap.books.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book,String> {

}
