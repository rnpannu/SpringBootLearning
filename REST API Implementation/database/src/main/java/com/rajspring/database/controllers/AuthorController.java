package com.rajspring.database.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.rajspring.database.domain.dto.AuthorDto;
import com.rajspring.database.domain.entities.AuthorEntity;
import com.rajspring.database.mappers.Mapper;
import com.rajspring.database.services.AuthorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class AuthorController {
    
    private AuthorService authorService;

    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }
    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) { // Converted from JSON to AuthorDto (JSON -> Presentation Layer) with Jackson and RequestBody
        // Look at http post requestbody for the object object (represented as JSON) to be sent to this endpoint.
        AuthorEntity authorEntity = authorMapper.mapFrom(author); // Map from presentation layer to service/persistence layer. 
        AuthorEntity savedEntity = authorService.save(authorEntity); // Create service/persistence layer entity
        return new ResponseEntity<>(authorMapper.mapTo(savedEntity), HttpStatus.CREATED); // Map from service/persistence layer to presentation layer, return an AuthorDto which is then mapped to the caller as JSON.
    }
    
    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors() {
        List<AuthorEntity> authors = authorService.findAll();
        return authors.stream().map(authorMapper::mapTo).collect(Collectors.toList()); // Map from service/persistence layer to presentation layer
    
    }
    
    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id) {
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);
        return foundAuthor.map(authorEntity -> {
            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto author) {
        //TODO: process PUT request
        if(!authorService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        author.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(author); // Map from presentation layer to service/persistence layer. 
        AuthorEntity savedEntity = authorService.save(authorEntity); // Create service/persistence layer entity
        return new ResponseEntity<>(authorMapper.mapTo(savedEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto author) {
        if(!authorService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorEntity authorEntity = authorMapper.mapFrom(author); // Map from presentation layer to service/persistence layer. 
        AuthorEntity updatedAuthor = authorService.partialUpdate(id, authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthor), HttpStatus.OK);
        //AuthorEntity savedEntity = authorService.save(authorEntity); // Create service/persistence layer entity

    }

    @DeleteMapping(path = "authors/{id}")
    public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable("id") Long id) {
        if(!authorService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
