package com.rajspring.database.dao;

import java.util.List;
import java.util.Optional;

import com.rajspring.database.domain.Book;

public interface BookDao {
    void create(Book book);
    Optional<Book> findOne(String isbn);
    List<Book> find();
    void update(String isbn, Book book);
    void delete(String isbn);
}
