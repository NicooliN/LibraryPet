package ru.pet.library.librarypet.library.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pet.library.librarypet.library.dto.BookDTO;
import ru.pet.library.librarypet.library.model.Book;

@Component
public class BookMapper
        extends GenericMapper<Book, BookDTO> {

    protected BookMapper(ModelMapper modelMapper) {
        super(modelMapper, Book.class, BookDTO.class);
    }

}
