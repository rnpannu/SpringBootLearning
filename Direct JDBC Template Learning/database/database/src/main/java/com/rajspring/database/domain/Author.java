package com.rajspring.database.domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author {
    private Long id; // Long can be null but long defaults to 0
    private String name;
    private Integer age;

}
