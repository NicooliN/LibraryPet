package ru.pet.library.librarypet.library.controller;

import org.springframework.stereotype.Controller;
import ru.pet.library.librarypet.library.dto.AuthorDTO;
import ru.pet.library.librarypet.library.model.Author;
import ru.pet.library.librarypet.library.repository.AuthorRepository;
import ru.pet.library.librarypet.library.service.AuthorService;

@Controller
public class AuthorController
        extends GenericController<Author, AuthorDTO> {



    protected AuthorController(AuthorService authorService){
        super(authorService);
    }
}
