package ru.pet.library.librarypet.library.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.pet.library.librarypet.library.dto.AddBookDTO;
import ru.pet.library.librarypet.library.dto.AuthorDTO;
import ru.pet.library.librarypet.library.exception.MyDeleteException;
import ru.pet.library.librarypet.library.mapper.AuthorMapper;
import ru.pet.library.librarypet.library.model.Author;
import ru.pet.library.librarypet.library.repository.AuthorRepository;
import ru.pet.library.librarypet.library.repository.GenericRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class AuthorServiceTest
        extends GenericTest<Author, AuthorDTO>{

    private final AuthorRepository authorRepository = Mockito.mock(AuthorRepository.class);

    private  final AuthorMapper authorMapper = Mockito.mock(AuthorMapper.class);

    public AuthorServiceTest(){
        super();
        service = new AuthorService(authorRepository, authorMapper);
    }

    @Test
    @Order(1)
    @Override
    protected void getAll() {
        Mockito.when(authorRepository.findAll()).thenReturn(AuthorTestData.AUTHOR_LIST);
        Mockito.when(authorMapper.toDtos(AuthorTestData.AUTHOR_LIST)).thenReturn(AuthorTestData.AUTHOR_DTO_LIST);
        List<AuthorDTO> authorDTOS = service.listAll();
//        System.out.println(authorDTOS);
        assertEquals(AuthorTestData.AUTHOR_LIST.size(), authorDTOS.size());
    }

    @Test
    @Order(2)
    @Override
    protected void getOne() {
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));
        Mockito.when(authorMapper.toDto(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_DTO_1);
        AuthorDTO authorDTO = service.getOne(1L);
        log.info("Testing getOne(): " + authorDTO);
        assertEquals(AuthorTestData.AUTHOR_DTO_1, authorDTO);
    }

    @Order(3)
    @Test
    @Override
    protected void create() {
        Mockito.when(authorMapper.toEntity(AuthorTestData.AUTHOR_DTO_1)).thenReturn(AuthorTestData.AUTHOR_1);
        Mockito.when(authorMapper.toDto(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_DTO_1);
        Mockito.when(authorRepository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);
        AuthorDTO authorDTO = service.create(AuthorTestData.AUTHOR_DTO_1);
        log.info("Testing create(): " + authorDTO);
        assertEquals(AuthorTestData.AUTHOR_DTO_1, authorDTO);
    }

    @Order(4)
    @Test
    @Override
    protected void update() {
        Mockito.when(authorMapper.toEntity(AuthorTestData.AUTHOR_DTO_1)).thenReturn(AuthorTestData.AUTHOR_1);
        Mockito.when(authorMapper.toDto(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_DTO_1);
        Mockito.when(authorRepository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);
        AuthorDTO authorDTO = service.update(AuthorTestData.AUTHOR_DTO_1);
        log.info("Testing update(): " + authorDTO);
        assertEquals(AuthorTestData.AUTHOR_DTO_1, authorDTO);
    }

    @Order(5)
    @Test
    @Override
    protected void delete() throws MyDeleteException {
        Mockito.when(authorRepository.checkAuthorForDeletion(1L)).thenReturn(true);
//        Mockito.when(authorRepository.checkAuthorForDeletion(2L)).thenReturn(false);
        Mockito.when(authorRepository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));
        log.info("Testing delete() before: " + AuthorTestData.AUTHOR_1.isDeleted());
        service.delete(1L);
        log.info("Testing delete() after: " + AuthorTestData.AUTHOR_1.isDeleted());
        assertTrue(AuthorTestData.AUTHOR_1.isDeleted());
    }

    @Order(6)
    @Test
    @Override
    protected void restore() {
        AuthorTestData.AUTHOR_3.setDeleted(true);
        Mockito.when(authorRepository.save(AuthorTestData.AUTHOR_3)).thenReturn(AuthorTestData.AUTHOR_3);
        Mockito.when(authorRepository.findById(3L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_3));
        log.info("Testing restore() before: " + AuthorTestData.AUTHOR_3.isDeleted());
        ((AuthorService) service).restore(3L);
        log.info("Testing restore() after: " + AuthorTestData.AUTHOR_3.isDeleted());
        assertFalse(AuthorTestData.AUTHOR_3.isDeleted());
    }

    @Order(7)
    @Test
    void searchAuthors() {
        PageRequest pageRequest = PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "authorFio"));
        Mockito.when(authorRepository.findAllByAuthorFioContainsIgnoreCaseAndIsDeletedFalse("authorFio1", pageRequest))
                .thenReturn(new PageImpl<>(AuthorTestData.AUTHOR_LIST));
        Mockito.when(authorMapper.toDtos(AuthorTestData.AUTHOR_LIST)).thenReturn(AuthorTestData.AUTHOR_DTO_LIST);
        Page<AuthorDTO> authorDTOList = ((AuthorService) service).searchAuthors("authorFio1", pageRequest);
        log.info("Testing searchAuthors(): " + authorDTOList);
        assertEquals(AuthorTestData.AUTHOR_DTO_LIST, authorDTOList.getContent());
    }

    @Order(8)
    @Test
    void addBook() {
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));
        Mockito.when(service.getOne(1L)).thenReturn(AuthorTestData.AUTHOR_DTO_1);
        Mockito.when(authorRepository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);
        ((AuthorService) service).addBook(new AddBookDTO(1L, 1L));
        log.info("Testing addBook(): " + AuthorTestData.AUTHOR_DTO_1.getBooksIds());
        assertTrue(AuthorTestData.AUTHOR_DTO_1.getBooksIds().size() >= 1);
    }
}
