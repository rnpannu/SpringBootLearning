package com.rajspring.database.services;


import java.util.List;
import java.util.Optional;

import com.rajspring.database.domain.dto.AuthorDto;
import com.rajspring.database.domain.entities.AuthorEntity;

public interface AuthorService {
    AuthorEntity save(AuthorEntity author);
    List<AuthorEntity> findAll();
    Optional<AuthorEntity> findOne(Long id);
    boolean exists(Long id);
    AuthorEntity partialUpdate(Long id, AuthorEntity author);
    void delete(Long id);
}
