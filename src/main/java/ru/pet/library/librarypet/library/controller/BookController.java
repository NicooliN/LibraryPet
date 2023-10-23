package ru.pet.library.librarypet.library.controller;

import groovy.util.logging.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pet.library.librarypet.library.dto.BookDTO;
import ru.pet.library.librarypet.library.dto.BookSearchDTO;
import ru.pet.library.librarypet.library.dto.BookWithAuthorsDTO;

import ru.pet.library.librarypet.library.service.BookService;

import java.util.List;

@Controller
@Slf4j
@RequestMapping(value = "/books")
public class BookController
        {

    private final BookService bookService;

    protected BookController(BookService bookService) {
        this.bookService =bookService;
    }

    @GetMapping("")
            public String getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "size", defaultValue = "5") int pageSize,
                                 Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "bookTitle"));
        Page<BookWithAuthorsDTO> result = bookService.getAllBooksWithAuthors(pageRequest);
        model.addAttribute("books", result);
        return "books/viewAllBooks";
    }
            @GetMapping("/{id}")
            public String getOne(@PathVariable Long id,
                                 Model model) {
                model.addAttribute("book", bookService.getBookWithAuthors(id));
                return "books/viewBook";
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


            @PostMapping("/search")
            public String searchBooks(@RequestParam(value = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "size", defaultValue = "5") int pageSize,
                                      @ModelAttribute("bookSearchForm") BookSearchDTO bookSearchDTO,
                                      Model model) {
                PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "title"));
                model.addAttribute("books", bookService.findBooks(bookSearchDTO, pageRequest));
                return "books/viewAllBooks";
            }
}


