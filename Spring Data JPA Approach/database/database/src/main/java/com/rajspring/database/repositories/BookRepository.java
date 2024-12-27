package com.rajspring.database.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rajspring.database.domain.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
    
}
