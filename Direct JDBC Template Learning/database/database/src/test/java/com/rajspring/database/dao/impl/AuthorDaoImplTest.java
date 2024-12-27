package com.rajspring.database.dao.impl;

import com.rajspring.database.TestDataUtil;
import com.rajspring.database.dao.impl.AuthorDaoImpl;
import com.rajspring.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import org.mockito.ArgumentMatchers;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import org.springframework.jdbc.core.RowMapper;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private AuthorDaoImpl underTest; // New instance of tested (mock) object is created, and then (mock) dependencies are injected into it

    @Test
    public void testThatCreateAuthorGeneratesCorrectSql(){
        Author author = createTestAuthor();
        underTest.create(author);
        verify(jdbcTemplate).update(eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(1L),eq("Abigail Rose"),eq(80));
    }
    private static Author createTestAuthor() {
        return Author.builder().id(1L).name("Abigail Rose").age(80).build();
    }
    @Test
    public void testThatFindOneAuthorGeneratesTheCorrectSql(){
        underTest.findOne(1L);
        verify(jdbcTemplate).query(eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"), ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(), eq(1L));
    }

    @Test
    public void testThatFindManyGeneratesTheCorrectSql(){
        underTest.find();
        verify(jdbcTemplate).query(eq("SELECT id, name, age FROM authors"), ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any());
    }
    @Test
    public void testThatUpdateGeneratesTheCorrectSql(){
        Author authorA = TestDataUtil.createTestAuthorA();

        underTest.update(3L, authorA);
        verify(jdbcTemplate).update(eq("UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?"), eq(1L), eq("Abigail Rose"), eq(80), eq(3L));
    }
    @Test
    public void testThatDeleteGeneratesTheCorrectSql(){
        underTest.delete(1L);// 3L?
        verify(jdbcTemplate).update(eq("DELETE FROM authors WHERE id = ?"), eq(1L));
    }
}
