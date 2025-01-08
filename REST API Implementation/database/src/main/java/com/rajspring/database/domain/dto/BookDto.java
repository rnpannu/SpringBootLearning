package com.rajspring.database.domain.dto;

import com.rajspring.database.domain.entities.AuthorEntity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

    private String isbn;
    private String title;
    private AuthorDto author;
}
