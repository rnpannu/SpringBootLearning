package com.rajspring.database.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import com.rajspring.database.domain.entities.BookEntity;

public interface BookService {
    public BookEntity createUpdateBook(String isbn, BookEntity bookEntity);
    List<BookEntity> findAll();
    Page<BookEntity> findAll(Pageable pageable);
    Optional<BookEntity> findOne(String isbn);
    boolean exists(String isbn);
    BookEntity partialUpdate(String isbn, BookEntity bookEntity);
    void delete(String isbn);
}
