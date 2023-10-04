package ru.pet.library.librarypet.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pet.library.librarypet.library.dto.BookDTO;
import ru.pet.library.librarypet.library.dto.BookWithAuthorsDTO;

import ru.pet.library.librarypet.library.service.BookService;

import java.util.List;

@Controller
@RequestMapping(value = "/books")
public class BookController
        {

    private final BookService bookService;

    protected BookController(BookService bookService) {
        this.bookService =bookService;
    }

    @GetMapping("")
            public String getAll(Model model) {
        List<BookWithAuthorsDTO> bookDTOList = bookService.getAllBookWithAuthors();
        model.addAttribute("books", bookDTOList);
        return "books/viewAllBooks";
    }

    @GetMapping("/add")
    public String create() {
        return "books/addBook";
    }

    @PostMapping("/add")
                        public String create(@ModelAttribute("bookForm") BookDTO bookDTO) {
                    bookService.create(bookDTO);
                    return "redirect:/books";
    }

}


