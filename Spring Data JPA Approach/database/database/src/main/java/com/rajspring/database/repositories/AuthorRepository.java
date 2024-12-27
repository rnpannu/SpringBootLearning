package com.rajspring.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rajspring.database.domain.Author;

@Repository // very similar to a @Component annotation, but it is used to mark the repository layer in a Spring application. Author is now a bean that can be injected wherever needed.
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Iterable<Author> ageLessThan(int i);
    @Query("SELECT a FROM Author a WHERE a.age > ?1") // Select an author object where the age is greater than the first parameter
    Iterable<Author> findAuthorsWithAgeGreaterThan(int i);

}
