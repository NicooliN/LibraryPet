package ru.pet.library.librarypet.library.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pet.library.librarypet.library.dto.AuthorDTO;
import ru.pet.library.librarypet.library.dto.BookDTO;
import ru.pet.library.librarypet.library.model.Author;
import ru.pet.library.librarypet.library.model.Book;
import ru.pet.library.librarypet.library.model.GenericModel;
import ru.pet.library.librarypet.library.repository.BookRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorMapper
        extends GenericMapper<Author, AuthorDTO>{

    private final BookRepository bookRepository;
    protected AuthorMapper(ModelMapper modelMapper,
                           BookRepository bookRepository) {
        super(modelMapper, Author.class, AuthorDTO.class);
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Author.class, AuthorDTO.class)
                .addMappings(m -> m.skip(AuthorDTO::setBooksIds)).setPostConverter(toDTOConverter());
        modelMapper.createTypeMap(AuthorDTO.class, Author.class)
                .addMappings(m -> m.skip(Author::setBooks)).setPostConverter(toEntityConverter());
    }
    @Override
    protected void mapSpecificFields(AuthorDTO source, Author destination) {
        if(!Objects.isNull(source.getBooksIds())) {
            destination.setBooks(new HashSet<>(bookRepository.findAllById(source.getBooksIds())));
        }
        else {
            destination.setBooks(Collections.emptySet());
        }
    }

    @Override
    protected void mapSpecificFields(Author source, AuthorDTO destination) {
    destination.setBooksIds(getIds(source));
    }

    @Override
    protected Set<Long> getIds(Author entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getBooks())
                ? Collections.emptySet()
                : entity.getBooks().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}
