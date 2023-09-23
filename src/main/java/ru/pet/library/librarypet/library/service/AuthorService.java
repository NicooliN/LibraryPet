package ru.pet.library.librarypet.library.service;

import org.springframework.stereotype.Service;
import ru.pet.library.librarypet.library.dto.AuthorDTO;
import ru.pet.library.librarypet.library.mapper.AuthorMapper;
import ru.pet.library.librarypet.library.model.Author;
import ru.pet.library.librarypet.library.repository.AuthorRepository;
@Service
public class AuthorService
        extends GenericService<Author, AuthorDTO> {

    protected AuthorService(AuthorRepository authorRepository,
                            AuthorMapper authorMapper) {
        super(authorRepository, authorMapper);
    }


}
