package com.rajspring.database.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.rajspring.database.domain.entities.AuthorEntity;
import com.rajspring.database.repositories.AuthorRepository;
import com.rajspring.database.services.AuthorService;

@Service //Bean
public class AuthorServiceImpl implements AuthorService{
    private AuthorRepository authorRepository; // Repository == data access layer object
    private AuthorServiceImpl(AuthorRepository authorRepository) { // Constructor injection
        this.authorRepository = authorRepository;
    }
    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity); // Save to database
        // Returns authorEntity because Spring Data JPA returns the saved entity.
    }

    @Override
    public List<AuthorEntity> findAll() {
        // Use stream support to convert the iterable to a list
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false).collect(Collectors.toList());

    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean exists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        authorEntity.setId(id);
        return authorRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author does not exist"));
        
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
