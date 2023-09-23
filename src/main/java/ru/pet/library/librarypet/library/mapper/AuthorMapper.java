package ru.pet.library.librarypet.library.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pet.library.librarypet.library.dto.AuthorDTO;
import ru.pet.library.librarypet.library.model.Author;

@Component
public class AuthorMapper
        extends GenericMapper<Author, AuthorDTO>{

    protected AuthorMapper(ModelMapper modelMapper) {
        super(modelMapper, Author.class, AuthorDTO.class);
    }
}
