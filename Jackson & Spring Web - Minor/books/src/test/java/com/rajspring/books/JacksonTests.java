package com.rajspring.books;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajspring.books.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonTests {
    @Test
    public void testThatObjectMapperCanCreateJsonFromJavaObject() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = Book.builder()
                .isbn("978-3-16-148410-5")
                .title("The Enigma of Eternity")
                .author("Aria Montgomery")
                .yearPublished("2005")
                .build();

        String result = objectMapper.writeValueAsString(book);
        assertThat(result).isEqualTo("{\"isbn\":\"978-3-16-148410-5\",\"title\":\"The Enigma of Eternity\",\"author\":\"Aria Montgomery\",\"year\":\"2005\"}");
    }

    @Test
    public void testThatObjectMapperCanCreateJavaObjectFromJsonObject() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"foo\":\"bar\", \"isbn\":\"978-3-16-148410-5\",\"title\":\"The Enigma of Eternity\",\"author\":\"Aria Montgomery\",\"year\":\"2005\"}";

        Book book = objectMapper.readValue(json, Book.class);
        assertThat(book).isEqualTo(Book.builder()
                .isbn("978-3-16-148410-5")
                .title("The Enigma of Eternity")
                .author("Aria Montgomery")
                .yearPublished("2005")
                .build());
    }


}
