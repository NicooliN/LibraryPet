package ru.pet.library.librarypet.library.service;

import ru.pet.library.librarypet.library.dto.AuthorDTO;
import ru.pet.library.librarypet.library.model.Author;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public interface AuthorTestData {

    AuthorDTO AUTHOR_DTO_1 = new AuthorDTO("authorfio1",
            "birthDate1",
            "description",
            new HashSet<>(),
            false);

    AuthorDTO AUTHOR_DTO_2 = new AuthorDTO("authorfio2",
            "birthDate2",
            "description2",
            new HashSet<>(),
            false);

    AuthorDTO AUTHOR_DTO_3 = new AuthorDTO("authorfio3",
            "birthDate3",
            "description3",
            new HashSet<>(),
            true);

    List<AuthorDTO>  AUTHOR_DTO_LIST = Arrays.asList(AUTHOR_DTO_1, AUTHOR_DTO_2, AUTHOR_DTO_3);

    Author AUTHOR_1 = new Author("author1",
            LocalDate.now(),
            "description",
            null);

    Author AUTHOR_2 = new Author("author2",
            LocalDate.now(),
            "description2",
           null);

    Author  AUTHOR_3 = new Author("author3",
            LocalDate.now(),
            "description3",
            null);
    List<Author>  AUTHOR_LIST = Arrays.asList(AUTHOR_1, AUTHOR_2, AUTHOR_3);

}