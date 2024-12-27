package com.rajspring.database.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rajspring.database.TestDataUtil;
import com.rajspring.database.dao.AuthorDao;
import com.rajspring.database.domain.Author;
import com.rajspring.database.domain.Book;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTest {
    private BookDaoImpl underTest;
    private AuthorDao authorDao;

    @Autowired
    public BookDaoImplIntegrationTest(BookDaoImpl underTest, AuthorDao authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }
    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Book book = TestDataUtil.createTestBookA();
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);
        book.setAuthorId(author.getId());
        underTest.create(book);
        //underTest.findOne(book.getId());
        Optional<Book> result = underTest.findOne(book.getIsbn());// 
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);

    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);
        Book bookA = TestDataUtil.createTestBookA();
        Book bookB = TestDataUtil.createTestBookB();
        Book bookC = TestDataUtil.createTestBookC();
        bookA.setAuthorId(author.getId());
        bookB.setAuthorId(author.getId());
        bookC.setAuthorId(author.getId());
        underTest.create(bookA);
        underTest.create(bookB);
        underTest.create(bookC);
        assertThat(underTest.find()).hasSize(3).containsExactly(bookA, bookB, bookC);

    }

    @Test
    public void testThatBookCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);
        Book book = TestDataUtil.createTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);
        book.setTitle("UPDATEDTITLE");
        underTest.update(book.getIsbn(), book);
        
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);
        Book book = TestDataUtil.createTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);
        underTest.delete(book.getIsbn());
        assertThat(underTest.findOne(book.getIsbn())).isEmpty();
    }
}
