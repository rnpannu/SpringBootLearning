package com.rajspring.database.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book {
    @Id
    private String isbn;
    private String title;

    @ManyToOne(cascade = CascadeType.ALL) // All changes are persisted in the database
    @JoinColumn(name = "author_id") // Recognize authorid as the foreign key entity
    private Author author;
}
