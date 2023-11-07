package ru.pet.library.librarypet.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pet.library.librarypet.library.model.Author;

@Repository
public interface AuthorRepository
        extends GenericRepository<Author> {

    Page<Author> findAllByIsDeletedFalse(Pageable pageable);

    Page<Author> findAllByAuthorFioContainsIgnoreCaseAndIsDeletedFalse(String fio,
                                                                       Pageable pageable);

    @Query(value = """
          select case when count(a) > 0 then false else true end
          from Author a join a.books b
                        join BookRentInfo bri on b.id = bri.book.id
          where a.id = :authorId
          and bri.returned = false
          """)
    boolean checkAuthorForDeletion(final Long authorId);
}