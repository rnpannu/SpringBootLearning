package com.rajspring.database.dao.impl;

import com.rajspring.database.domain.Book;
import com.rajspring.database.dao.BookDao;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookDaoImpl implements BookDao {
    private final JdbcTemplate jdbcTemplate;
    public BookDaoImpl(final JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }
    public void create(Book book){
                (jdbcTemplate).update("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)",
                book.getIsbn(), book.getTitle(), book.getAuthorId());
    }
    public Optional<Book> findOne(String isbn){
        List<Book> results = jdbcTemplate.query("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1", new BookRowMapper(), isbn);
        return results.stream().findFirst();
    }

    public static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
            .isbn(rs.getString("isbn"))
            .title(rs.getString("title"))
            .authorId(rs.getLong("author_id"))
            .build();
        }
    }
    public List<Book> find(){
        return jdbcTemplate.query("SELECT isbn, title, author_id FROM books", new BookRowMapper());
    }
    public void update(String isbn, Book book){
        jdbcTemplate.update("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
        book.getIsbn(), book.getTitle(), book.getAuthorId(), isbn);
    }
    public void delete(String isbn){
        jdbcTemplate.update("DELETE FROM books WHERE isbn = ? ", isbn);
        
    }
}
