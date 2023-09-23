package ru.pet.library.librarypet.library.service;

import org.springframework.stereotype.Service;
import ru.pet.library.librarypet.library.dto.BookDTO;
import ru.pet.library.librarypet.library.mapper.BookMapper;
import ru.pet.library.librarypet.library.model.Book;
import ru.pet.library.librarypet.library.repository.BookRepository;

@Service
public class BookService
        extends GenericService<Book, BookDTO>{

    protected BookService (BookRepository bookRepository,
                        BookMapper bookMapper) {
      super(bookRepository, bookMapper);
    }


}
