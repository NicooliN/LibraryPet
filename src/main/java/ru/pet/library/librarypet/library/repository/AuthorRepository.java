package ru.pet.library.librarypet.library.repository;

import org.springframework.stereotype.Repository;
import ru.pet.library.librarypet.library.model.Author;

@Repository
public interface AuthorRepository
        extends GenericRepository<Author> {
}
