package ru.pet.library.librarypet.library.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.pet.library.librarypet.library.dto.AuthorDTO;
import ru.pet.library.librarypet.library.dto.BookDTO;
import ru.pet.library.librarypet.library.dto.BookSearchDTO;
import ru.pet.library.librarypet.library.dto.BookWithAuthorsDTO;

import ru.pet.library.librarypet.library.exception.MyDeleteException;
import ru.pet.library.librarypet.library.service.BookService;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static ru.pet.library.librarypet.library.constants.UserRolesConstants.ADMIN;

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
                                 @ModelAttribute(name = "exception") final String exception,
                                 Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "bookTitle"));
        Page<BookWithAuthorsDTO> result;
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (ADMIN.equalsIgnoreCase(userName)) {
            result = bookService.getAllBooksWithAuthors(pageRequest);
        }
        else {
            result = bookService.getAllNotDeletedBooksWithAuthors(pageRequest);
        }
        model.addAttribute("books", result);
        model.addAttribute("exception", exception);
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
                        public String create(@ModelAttribute("bookForm") BookDTO bookDTO,
                                             @RequestParam MultipartFile file) {
                    if (file != null && file.getSize() > 0) {
                        bookService.create(bookDTO, file);
                    } else {
                    bookService.create(bookDTO);
                    }
                    return "redirect:/books";
    }

            @GetMapping("/update/{id}")
            public String update(@PathVariable Long id,
                                 Model model) {
                model.addAttribute("book", bookService.getOne(id));
                return "books/updateBook";
            }

            @PostMapping("/update")
            public String update(@ModelAttribute("bookForm") BookDTO bookDTO,
                                 @RequestParam MultipartFile file) {
                if (file != null && file.getSize() > 0) {
                    bookService.update(bookDTO, file);
                }
                else {
                    bookService.update(bookDTO);
                }
                return "redirect:/books";
            }

            @GetMapping("/delete/{id}")
            public String delete(@PathVariable Long id) throws MyDeleteException {
                bookService.delete(id);
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

            @PostMapping("/search/author")
            public String searchBooks(@RequestParam(value = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "size", defaultValue = "5") int pageSize,
                                      @ModelAttribute("authorSearchForm") AuthorDTO authorDTO,
                                      Model model) {
                BookSearchDTO bookSearchDTO = new BookSearchDTO();
                bookSearchDTO.setAuthorFio(authorDTO.getAuthorFio());
                return searchBooks(page, pageSize, bookSearchDTO, model);
            }


            @GetMapping(value = "/download", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
            @ResponseBody
            public ResponseEntity<Resource> downloadBook(@Param(value = "bookId") Long bookId) throws IOException {
                BookDTO bookDTO = bookService.getOne(bookId);
                Path path = Paths.get(bookDTO.getOnlineCopyPath());
                ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
                return  ResponseEntity.ok()
                        .headers(this.headers(path.getFileName().toString()))
                        .contentLength(path.toFile().length())
                        .contentType(MediaType.parseMediaType("application/octet-stream"))
                        .body(resource);
            }

            private HttpHeaders headers(String name) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return headers;
            }
            @GetMapping("/restore/{id}")
            public String restore(@PathVariable Long id) {
                bookService.restore(id);
                return "redirect:/books";
            }
            @ExceptionHandler(MyDeleteException.class)
            public RedirectView handleError(HttpServletRequest req,
                                            Exception ex,
                                            RedirectAttributes redirectAttributes) {
                log.error("Запрос: " + req.getRequestURL() + " вызвал ошибку " + ex.getMessage());
                redirectAttributes.addFlashAttribute("exception", ex.getMessage());
                return new RedirectView("/books", true);
            }

}


