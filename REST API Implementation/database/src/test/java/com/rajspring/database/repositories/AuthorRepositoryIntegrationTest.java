package com.rajspring.database.repositories;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rajspring.database.TestDataUtil;
import com.rajspring.database.domain.entities.AuthorEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTest {
    private AuthorRepository underTest;
    
    @Autowired
    public AuthorRepositoryIntegrationTest(AuthorRepository underTest) {
        this.underTest = underTest;
    }
    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();
        underTest.save(author); // save = create
        Optional<AuthorEntity> result = underTest.findById(author.getId()); //  findById = findOne
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);

    }

    @Test 
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        AuthorEntity authorA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity authorB = TestDataUtil.createTestAuthorEntityB();
        AuthorEntity authorC = TestDataUtil.createTestAuthorEntityC();

        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);
        Iterable<AuthorEntity> result = underTest.findAll();
        assertThat(result).hasSize(3).containsExactly(authorA, authorB, authorC);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        AuthorEntity authorA = TestDataUtil.createTestAuthorEntityA();
        underTest.save(authorA);
        authorA.setName("UPDATEDNAME");
        underTest.save(authorA);
        
        Optional<AuthorEntity> result = underTest.findById(authorA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        AuthorEntity authorA = TestDataUtil.createTestAuthorEntityA();
        underTest.save(authorA);
        // underTest.delete(authorA); simpler
        underTest.deleteById(authorA.getId());
        Optional<AuthorEntity> result = underTest.findById(authorA.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan(){
        // Author testAuthorA = TestDataUtil.createTestAuthorA();
        // underTest.save(testAuthorA);
        // Author testAuthorB = TestDataUtil.createTestAuthorB();
        // underTest.save(testAuthorB);
        // Author testAuthorC = TestDataUtil.createTestAuthorC();
        // underTest.save(testAuthorC);

        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorEntityB();
        AuthorEntity testAuthorC = TestDataUtil.createTestAuthorEntityC();
        underTest.save(testAuthorA);
        underTest.save(testAuthorB);
        underTest.save(testAuthorC);
        // findAll returns iterable in CrudRepository
        Iterable<AuthorEntity> result = underTest.ageLessThan(50); // Spring Data JPA is smart
        assertThat(result).containsExactly(testAuthorB, testAuthorC);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan(){
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorEntityB();
        AuthorEntity testAuthorC = TestDataUtil.createTestAuthorEntityC();
        underTest.save(testAuthorA);
        underTest.save(testAuthorB);
        underTest.save(testAuthorC);

        Iterable<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(50);
        assertThat(result).containsExactly(testAuthorA);
    }
}
