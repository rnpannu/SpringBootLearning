package com.rajspring.database.controllers;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajspring.database.TestDataUtil;
import com.rajspring.database.domain.dto.AuthorDto;
import com.rajspring.database.domain.entities.AuthorEntity;
import com.rajspring.database.services.AuthorService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc // Creates an instance of Mock MVC and places in in the application context
public class AuthorControllerIntegrationTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private AuthorService authorService;

    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.authorService = authorService;
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA();
        testAuthorA.setId(null); // Post will generate id
        String authorJson = objectMapper.writeValueAsString(testAuthorA); // Write as JSON
        mockMvc.perform(
            MockMvcRequestBuilders.post("/authors")
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(authorJson))
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA();
        testAuthorA.setId(null); // Post will generate id
        String authorJson = objectMapper.writeValueAsString(testAuthorA);
        mockMvc.perform(
            MockMvcRequestBuilders.post("/authors")
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(authorJson))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("80"));
    } 

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA(); // Create test check
        authorService.save(testAuthorA);
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Abigail Rose"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value("80"));
            // .andExpect(MockMvcResultMatchers.status().isOk())
            // .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
            // .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value((Object) null))
            // .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value((Object) null))
            // .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").isNumber())
            // .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Aria Montgomery"))
            // .andExpect(MockMvcResultMatchers.jsonPath("$[1].age").value(80));
    }
    @Test
    public void testThatGetAuthorsReturnsHttpStatus200WhenAuthorExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA(); // Create test check
        authorService.save(testAuthorA);
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAuthorsReturnsHttpStatus404WhenNoAuthorExists() throws Exception {

        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetAuthorsReturnsAuthorWhenAuthorExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA(); // Create test check
        authorService.save(testAuthorA);
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("80"));
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus404WhenNoAuthorExist() throws Exception{
        AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA();
        String authorJson = objectMapper.writeValueAsString(testAuthorA);
        mockMvc.perform(
            MockMvcRequestBuilders.put("/authors/99")
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(authorJson))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus200WhenAuthorExist() throws Exception{
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntityA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);
        mockMvc.perform(
            MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(authorDtoJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatFullUpdateAuthorReturnsExistingAuthor() throws Exception{
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntityA);

        AuthorDto testAuthorDtoB = TestDataUtil.createTestAuthorDtoB();
        testAuthorDtoB.setId(savedAuthor.getId());
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoB);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(authorDtoJson))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Thomas Cronin"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("44"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateAuthorReturnsHttpStatus200WhenAuthorExist() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntityA);
        
        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        testAuthorDtoA.setName("UPDATED");
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);
        mockMvc.perform(
            MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(authorDtoJson))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatPartialUpdateAuthorReturnsUpdatedAuthor() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntityA);
        
        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        testAuthorDtoA.setName("UPDATED");
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(authorDtoJson))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testAuthorDtoA.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("UPDATED"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(testAuthorDtoA.getAge()))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForNonExistingAutho() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntityA);
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/authors/" + savedAuthor.getId())
                                  .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    
}
