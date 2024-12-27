package com.rajspring.database.dao;

import java.util.List;
import java.util.Optional;

import com.rajspring.database.domain.Author;

public interface AuthorDao {
    void create(Author author);
    Optional<Author> findOne(long l);
    List<Author> find();
    void update(Long id, Author author);
    void delete(Long id);
}