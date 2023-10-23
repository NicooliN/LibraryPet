package ru.pet.library.librarypet.library.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.pet.library.librarypet.library.dto.BookDTO;
import ru.pet.library.librarypet.library.dto.BookSearchDTO;
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


    public Page<BookWithAuthorsDTO> getAllBooksWithAuthors(Pageable pageable) {
        Page<Book> booksPaginated = bookRepository.findAll(pageable);
        List<BookWithAuthorsDTO> result = bookWithAuthorsMapper.toDtos(booksPaginated.getContent());
        return new PageImpl<>(result, pageable, booksPaginated.getTotalElements());
    }

    public Page<BookWithAuthorsDTO> findBooks(BookSearchDTO bookSearchDTO,
                                              Pageable pageable) {
        String genre = bookSearchDTO.getGenre() != null ? String.valueOf(bookSearchDTO.getGenre().ordinal()) : null;
        Page<Book> booksPaginated = bookRepository.searchBooks(genre,
                bookSearchDTO.getBookTitle(),
                bookSearchDTO.getAuthorFio(),
                pageable
        );
        List<BookWithAuthorsDTO> result = bookWithAuthorsMapper.toDtos(booksPaginated.getContent());
        return new PageImpl<>(result, pageable, booksPaginated.getTotalElements());
    }
    public BookWithAuthorsDTO getBookWithAuthors(Long id) {
        return bookWithAuthorsMapper.toDto(genericMapper.toEntity(super.getOne(id)));
    }
}
