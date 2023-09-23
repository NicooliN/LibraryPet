package ru.pet.library.librarypet.library.controller;

import org.springframework.stereotype.Controller;
import ru.pet.library.librarypet.library.dto.BookDTO;
import ru.pet.library.librarypet.library.model.Book;
import ru.pet.library.librarypet.library.service.BookService;

@Controller
public class BookController
        extends GenericController<Book, BookDTO> {

    private final BookService bookService;

    protected BookController(BookService bookService) {
        super(bookService);
        this.bookService =bookService;
    }
}
