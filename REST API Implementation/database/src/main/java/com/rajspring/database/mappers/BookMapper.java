package com.rajspring.database.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.rajspring.database.domain.dto.BookDto;
import com.rajspring.database.domain.entities.BookEntity;

@Component
public class BookMapper  implements Mapper<BookEntity, BookDto>{
    private ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity book) {
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }

}
