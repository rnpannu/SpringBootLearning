package com.rajspring.database.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rajspring.database.TestDataUtil;
import com.rajspring.database.domain.entities.AuthorEntity;
import com.rajspring.database.domain.entities.BookEntity;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTest {
    private BookRepository underTest;
    //private AuthorRepository authorDao;

    @Autowired
    public BookRepositoryIntegrationTest(BookRepository underTest) {
        this.underTest = underTest;
    }
    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        // Don't need to manually create author as cascade is set up, and no member setting is required
        // due to object changes
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();
        BookEntity book = TestDataUtil.createTestBookEntityA(author);
        
        underTest.save(book);

        Optional<BookEntity> result = underTest.findById(book.getIsbn());// 
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);

    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();
        BookEntity bookA = TestDataUtil.createTestBookEntityA(author);
        underTest.save(bookA);
        
        BookEntity bookB = TestDataUtil.createTestBookEntityB(author);
        underTest.save(bookB);
    
        BookEntity bookC = TestDataUtil.createTestBookEntityC(author);
        underTest.save(bookC);

        Iterable<BookEntity> books = underTest.findAll();
        assertThat(books).hasSize(3).containsExactly(bookA, bookB, bookC);

    }

    @Test
    public void testThatBookCanBeUpdated() {
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();
        BookEntity book = TestDataUtil.createTestBookEntityA(author);

        underTest.save(book);
        book.setTitle("UPDATEDTITLE");
        underTest.save(book);
        
        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();
        BookEntity book = TestDataUtil.createTestBookEntityA(author);
        underTest.save(book);
        underTest.deleteById(book.getIsbn());
        assertThat(underTest.findById(book.getIsbn())).isEmpty();
    }
}