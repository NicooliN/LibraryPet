package ru.pet.library.librarypet.library.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.pet.library.librarypet.library.constants.Errors;
import ru.pet.library.librarypet.library.dto.AddBookDTO;
import ru.pet.library.librarypet.library.dto.AuthorDTO;
import ru.pet.library.librarypet.library.exception.MyDeleteException;
import ru.pet.library.librarypet.library.mapper.AuthorMapper;
import ru.pet.library.librarypet.library.model.Author;
import ru.pet.library.librarypet.library.model.Book;
import ru.pet.library.librarypet.library.model.GenericModel;
import ru.pet.library.librarypet.library.repository.AuthorRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class AuthorService
        extends GenericService<Author, AuthorDTO> {

    private final AuthorRepository authorRepository;

    protected AuthorService(AuthorRepository authorRepository,
                            AuthorMapper authorMapper) {
        super(authorRepository, authorMapper);
        this.authorRepository = authorRepository;
    }

    public Page<AuthorDTO> listAllNotDeletedAuthors(Pageable pageable) {
        Page<Author> authors = authorRepository.findAllByIsDeletedFalse(pageable);
        List<AuthorDTO> result = genericMapper.toDtos(authors.getContent());
        return new PageImpl<>(result, pageable, authors.getTotalElements());
    }

    public Page<AuthorDTO> searchAuthors(final String fio,
                                         Pageable pageable) {
        Page<Author> authors = authorRepository.findAllByAuthorFioContainsIgnoreCaseAndIsDeletedFalse(fio, pageable);
        List<AuthorDTO> result = genericMapper.toDtos(authors.getContent());
        return new PageImpl<>(result, pageable, authors.getTotalElements());
    }

    public void addBook(AddBookDTO addBookDTO) {
        AuthorDTO author = getOne(addBookDTO.getAuthorId());
        author.getBooksIds().add(addBookDTO.getBookId());
        update(author);
    }

    @Override
    public void delete(Long objectId) throws MyDeleteException {
        Author author = authorRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Автора с заданным id=" + objectId + " не существует."));
        //TODO: пометить книги на удаление
        boolean authorCanBeDeleted = authorRepository.checkAuthorForDeletion(objectId);
        if (authorCanBeDeleted) {
            markAsDeleted(author);
            Set<Book> books = author.getBooks();
            if (books != null && !books.isEmpty()) {
            books.forEach(this::markAsDeleted);
            }
            authorRepository.save(author);
        }
        else {
            throw new MyDeleteException(Errors.Authors.AUTHOR_DELETE_ERROR);
        }
    }

    public void restore(Long objectId) {
        Author author = authorRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Автора с заданным id=" + objectId + " не существует."));
        unMarkAsDeleted(author);
        Set<Book> books = author.getBooks();
        if (books != null && !books.isEmpty()) {
            books.forEach(this::unMarkAsDeleted);
        }
        authorRepository.save(author);
    }

}
