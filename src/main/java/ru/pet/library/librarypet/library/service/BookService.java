package ru.pet.library.librarypet.library.service;

import org.springframework.stereotype.Service;
import ru.pet.library.librarypet.library.dto.BookDTO;
import ru.pet.library.librarypet.library.dto.BookWithAuthorsDTO;
import ru.pet.library.librarypet.library.mapper.BookMapper;
import ru.pet.library.librarypet.library.mapper.BookWithAuthorsMapper;
import ru.pet.library.librarypet.library.model.Book;
import ru.pet.library.librarypet.library.repository.BookRepository;

import java.util.List;

@Service
public class BookService
        extends GenericService<Book, BookDTO> {

    private final BookWithAuthorsMapper bookWithAuthorsMapper;
    private final BookRepository bookRepository;

    protected BookService (BookRepository bookRepository,
                        BookMapper bookMapper,
                           BookWithAuthorsMapper bookWithAuthorsMapper) {
      super(bookRepository, bookMapper);
      this.bookWithAuthorsMapper = bookWithAuthorsMapper;
      this.bookRepository = bookRepository;
    }


    public List<BookWithAuthorsDTO> getAllBookWithAuthors() {
        return bookWithAuthorsMapper.toDtos(bookRepository.findAll());
    }
}
