package ru.pet.library.librarypet.library.service;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.pet.library.librarypet.library.dto.BookDTO;
import ru.pet.library.librarypet.library.dto.BookRentInfoDTO;
import ru.pet.library.librarypet.library.mapper.BookRentInfoMapper;
import ru.pet.library.librarypet.library.model.BookRentInfo;
import ru.pet.library.librarypet.library.repository.BookRentInfoRepository;

import java.time.LocalDateTime;

@Service
public class BookRentInfoService
      extends GenericService<BookRentInfo, BookRentInfoDTO> {
    
    private final BookService bookService;
    private final BookRentInfoMapper bookRentInfoMapper;
    
    
    protected BookRentInfoService(BookRentInfoRepository bookRentInfoRepository,
                                  BookRentInfoMapper bookRentInfoMapper,
                                  BookService bookService) {
        super(bookRentInfoRepository, bookRentInfoMapper);
        this.bookService = bookService;
        this.bookRentInfoMapper = bookRentInfoMapper;
    }
    
    public BookRentInfoDTO rentBook(BookRentInfoDTO rentBookDTO) {
        BookDTO bookDTO = bookService.getOne(rentBookDTO.getBookId());
        bookDTO.setAmount(bookDTO.getAmount() - 1);
        bookService.update(bookDTO);
        long rentPeriod = rentBookDTO.getRentPeriod() != null ? rentBookDTO.getRentPeriod() : 14L;
        rentBookDTO.setRentDate(LocalDateTime.now());
        rentBookDTO.setReturned(false);
        rentBookDTO.setRentPeriod((int) rentPeriod);
        rentBookDTO.setReturnDate(LocalDateTime.now().plusDays(rentPeriod));
        rentBookDTO.setCreatedWhen(LocalDateTime.now());
        rentBookDTO.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return genericMapper.toDto(genericRepository.save(genericMapper.toEntity(rentBookDTO)));
    }

    public void returnBook(final Long id) {
        BookRentInfoDTO bookRentInfoDTO = getOne(id);
        bookRentInfoDTO.setReturned(true);
        bookRentInfoDTO.setReturnDate(LocalDateTime.now());
        BookDTO bookDTO = bookRentInfoDTO.getBookDTO();
        bookDTO.setAmount(bookDTO.getAmount() + 1);
        update(bookRentInfoDTO);
        bookService.update(bookDTO);
    }
}
