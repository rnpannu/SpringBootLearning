package com.rajspring.database;

import com.rajspring.database.domain.dto.AuthorDto;
import com.rajspring.database.domain.dto.BookDto;
import com.rajspring.database.domain.entities.AuthorEntity;
import com.rajspring.database.domain.entities.BookEntity;

public final class TestDataUtil {
    private TestDataUtil(){
    }

    public static AuthorEntity createTestAuthorEntityA() {
        return AuthorEntity.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static AuthorDto createTestAuthorDtoA() {
        return AuthorDto.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static AuthorEntity createTestAuthorEntityB() {
        return AuthorEntity.builder()
                .id(2L)
                .name("Thomas Cronin")
                .age(44)
                .build();
    }

    public static AuthorDto createTestAuthorDtoB() {
        return AuthorDto.builder()
                .id(2L)
                .name("Thomas Cronin")
                .age(44)
                .build();
    }


    public static AuthorEntity createTestAuthorEntityC() {
        return AuthorEntity.builder()
                .id(3L)
                .name("Jesse A Casey")
                .age(24)
                .build();
    }

    public static BookEntity createTestBookEntityA(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .author(author)
                .build();
    }
    public static BookDto createTestBookDtoA(final AuthorDto author) {
        return BookDto.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .author(author)
                .build();
    }


    public static BookEntity createTestBookEntityB(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-1")
                .title("Beyond the Horizon")
                .author(author)
                .build();
    }

    public static BookEntity createTestBookEntityC(final AuthorEntity author) {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-2")
                .title("The Last Ember")
                .author(author)
                .build();
    }
}
// import com.rajspring.database.domain.Author;
// import com.rajspring.database.domain.Book;

// public final class TestDataUtil{
//     private TestDataUtil() {
//     }
//     public static Author createTestAuthorA() {
//         return Author.builder().id(1L).name("Abigail Rose").age(80).build();
//     }
//     public static Author createTestAuthorB() {
//         return Author.builder().id(2L).name("Thomans Cronin").age(44).build();
//     }
//     public static Author createTestAuthorC() {
//         return Author.builder().id(3L).name("Jesse A Casey").age(24).build();
//     }
//     public static Book createTestBookA(final Author author) {
//         return Book.builder()
//             .isbn("978-1-2345-6789-0")
//             .title("The Shadow in the Attic")
//             .authorId(author)
//             .build();
//     }
//     public static Book createTestBookB(final Author author) {
//         return Book.builder()
//             .isbn("978-1-2345-6789-1")
//             .title("The Shadow in the Attic")
//             .authorId(author)
//             .build();
//     }
//     public static Book createTestBookC(final Author author) {
//         return Book.builder()
//             .isbn("978-1-2345-6789-2")
//             .title("The Shadow in the Attic")
//             .authorId(author)
//             .build();
//     }
// }
