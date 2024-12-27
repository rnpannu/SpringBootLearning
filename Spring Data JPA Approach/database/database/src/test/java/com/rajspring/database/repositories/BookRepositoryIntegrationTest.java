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
import com.rajspring.database.domain.Author;
import com.rajspring.database.domain.Book;

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
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookA(author);
        
        underTest.save(book);

        Optional<Book> result = underTest.findById(book.getIsbn());// 
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);

    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();
        Book bookA = TestDataUtil.createTestBookA(author);
        underTest.save(bookA);
        
        Book bookB = TestDataUtil.createTestBookB(author);
        underTest.save(bookB);
    
        Book bookC = TestDataUtil.createTestBookC(author);
        underTest.save(bookC);

        Iterable<Book> books = underTest.findAll();
        assertThat(books).hasSize(3).containsExactly(bookA, bookB, bookC);

    }

    @Test
    public void testThatBookCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookA(author);

        underTest.save(book);
        book.setTitle("UPDATEDTITLE");
        underTest.save(book);
        
        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookA(author);
        underTest.save(book);
        underTest.deleteById(book.getIsbn());
        assertThat(underTest.findById(book.getIsbn())).isEmpty();
    }
}