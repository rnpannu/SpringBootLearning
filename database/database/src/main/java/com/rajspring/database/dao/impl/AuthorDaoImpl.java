package com.rajspring.database.dao.impl;

import com.rajspring.database.dao.AuthorDao;
import com.rajspring.database.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class AuthorDaoImpl implements AuthorDao {
    private final JdbcTemplate jdbcTemplate;
    public AuthorDaoImpl(final JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public void create(Author author) {
        jdbcTemplate.update("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)",
        author.getId(), author.getName(), author.getAge());
    }
    @Override
    public Optional<Author> findOne(long l){
        // Optimize to not create a new instance of AuthorRowMapper every time
        List<Author> results = jdbcTemplate.query("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1", new AuthorRowMapper(), l);
        return results.stream().findFirst();
    }

    public static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .age(rs.getInt("age"))
            .build();
        }
    }
    public List<Author> find(){
        return jdbcTemplate.query("SELECT id, name, age FROM authors", new AuthorRowMapper());
    }
    public void update(Long id, Author author){
        jdbcTemplate.update("UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?",
        author.getId(), author.getName(), author.getAge(), id);
        // jdbcTemplate.update("UPDATE authors SET id = ? name = ?, age = ? WHERE id = ?",
        // id, author.getName(), author.getAge(), author.getId());
    }
    public void delete(Long id){
        jdbcTemplate.update("DELETE FROM authors WHERE id = ?", id);
    }
}

