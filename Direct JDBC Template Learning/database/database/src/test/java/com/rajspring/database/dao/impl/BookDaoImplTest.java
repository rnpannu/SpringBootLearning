package com.rajspring.database.dao.impl;


import com.rajspring.database.TestDataUtil;
import com.rajspring.database.dao.impl.BookDaoImpl;
import com.rajspring.database.domain.Book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.mockito.ArgumentMatchers;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)

public class BookDaoImplTest{
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreateBookGeneratesCorrectSql(){
        
        Book book = TestDataUtil.createTestBookA();

        underTest.create(book);
        
        verify(jdbcTemplate).update(eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"), eq("978-1-2345-6789-0"), eq("The Shadow in the Attic"), eq(1L));
    }
    

    @Test
    public void testThatFindOneGeneratesTheCorrectSql(){
        underTest.findOne("978-1-2345-6789-0");
        verify(jdbcTemplate).query(eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"), ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(), eq("978-1-2345-6789-0"));
    }
    @Test
    public void testThatFindGeneratesCorrectSql(){
        underTest.find();
        verify(jdbcTemplate).query(eq("SELECT isbn, title, author_id FROM books"), ArgumentMatchers.<BookDaoImpl.BookRowMapper>any());
    }

    @Test
    public void testThatUpdateGeneratesTheCorrectSql(){
        Book bookA = TestDataUtil.createTestBookA();

        underTest.update("978-1-2345-6789-0", bookA);
        verify(jdbcTemplate).update(eq("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?"), eq("978-1-2345-6789-0"), eq("The Shadow in the Attic"), eq(1L), eq("978-1-2345-6789-0"));
    }  
    @Test
    public void testThatDeleteGeneratesTheCorrectSql(){
        underTest.delete("978-1-2345-6789-0");
        verify(jdbcTemplate).update(eq("DELETE FROM books WHERE isbn = ? "), eq("978-1-2345-6789-0"));
    }

}