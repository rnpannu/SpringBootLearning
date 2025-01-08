package com.rajspring.database.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajspring.database.TestDataUtil;
import com.rajspring.database.domain.dto.AuthorDto;
import com.rajspring.database.domain.dto.BookDto;
import com.rajspring.database.domain.entities.AuthorEntity;
import com.rajspring.database.domain.entities.BookEntity;
import com.rajspring.database.services.BookService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc 
public class BookControllerIntegrationTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTest(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookReturnsHttp201Created() throws Exception {
        // AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA();
        // testAuthorA.setId(null);
        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        //testBookA.setIsbn("1234567890");
        String bookJson = objectMapper.writeValueAsString(testBookA);
        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/" + testBookA.getIsbn())
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(bookJson))
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatUpdateBookReturnsHttp200OK() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        //testBookA.setIsbn(savedBook.getIsbn()); // For convention
        String bookJson = objectMapper.writeValueAsString(testBookA);
        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/" + testBookA.getIsbn())
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(bookJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        testBookA.setIsbn(savedBook.getIsbn()); // For convention
        testBookA.setTitle("Updated");
        String bookJson = objectMapper.writeValueAsString(testBookA);
        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/" + testBookA.getIsbn())
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(bookJson))
            .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated"))
            //.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("The Shadow in the Attic"))
            //.andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Abigail Rose"));
            .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testThatCreateBookReturnsCreatedBook() throws Exception {
        // AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        // testAuthorA.setId(null); // Post will generate id
        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        // testBookA.setIsbn("1234567890");
        String bookJson = objectMapper.writeValueAsString(testBookA);
        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/" + testBookA.getIsbn())
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(bookJson))
            .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(testBookA.getIsbn()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(testBookA.getTitle()));  
    }  


    @Test
    public void testThatListBooksReturnsHttpStatus200Ok() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/books").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    // @Test
    // public void testThatListBooksReturnsListOfBooks() throws Exception {
    //     //AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA(); 
    //     BookEntity testBookA = TestDataUtil.createTestBookEntityA(null); 
    //     bookService.createUpdateBook(testBookA.getIsbn(), testBookA);
    //     mockMvc.perform(
    //         MockMvcRequestBuilders.get("/books").contentType(MediaType.APPLICATION_JSON))
    //         .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value("978-1-2345-6789-0"))
    //         .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("The Shadow in the Attic"));
    //         //.andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("Abigail Rose"));
    // }

    @Test
    public void testThatGetBooksReturnsHttpStatus200WhenBookExists() throws Exception {
        BookEntity testBookA = TestDataUtil.createTestBookEntityA(null);
        bookService.createUpdateBook(testBookA.getIsbn(), testBookA);
        mockMvc.perform(
            MockMvcRequestBuilders.get("/books/" + testBookA.getIsbn()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetBooksReturnsHttpStatus404WhenNoBookExists() throws Exception {
        BookEntity testBookA = TestDataUtil.createTestBookEntityA(null);
        //bookService.createBook(testBookA.getIsbn(), testBookA);
        mockMvc.perform(
            MockMvcRequestBuilders.get("/books/" + testBookA.getIsbn()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatPartialUpdateBookReturnsHttpStatus200WhenBookExist() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);
        
        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(null);
        testBookDtoA.setTitle("UPDATED");
        String bookDtoJson = objectMapper.writeValueAsString(testBookDtoA);
        mockMvc.perform(
            MockMvcRequestBuilders.patch("/books/" + testBookEntityA.getIsbn())
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(bookDtoJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatPartialUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);
        
        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(null);
        testBookDtoA.setTitle("UPDATED");
        String bookDtoJson = objectMapper.writeValueAsString(testBookDtoA);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(bookDtoJson))
            .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(testBookDtoA.getIsbn()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("UPDATED"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testThatDeleteBookReturnsHttpStatus204WhenBookExists() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/books/" + savedBook.getIsbn()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
