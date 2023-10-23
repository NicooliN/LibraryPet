package ru.pet.library.librarypet.library.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pet.library.librarypet.library.dto.BookDTO;
import ru.pet.library.librarypet.library.dto.BookWithAuthorsDTO;
import ru.pet.library.librarypet.library.model.Book;
import ru.pet.library.librarypet.library.model.GenericModel;
import ru.pet.library.librarypet.library.repository.AuthorRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookWithAuthorsMapper
                extends GenericMapper<Book, BookWithAuthorsDTO> {

    private final AuthorRepository authorRepository;

    protected BookWithAuthorsMapper(ModelMapper modelMapper,
                         AuthorRepository authorRepository) {

        super(modelMapper, Book.class, BookWithAuthorsDTO.class);
        this.authorRepository = authorRepository;
    }
    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Book.class, BookWithAuthorsDTO.class)
                .addMappings(m -> m.skip(BookWithAuthorsDTO::setAuthorsIds)).setPostConverter(toDTOConverter());
        modelMapper.createTypeMap(BookWithAuthorsDTO.class, Book.class)
                .addMappings(m -> m.skip(Book::setAuthors)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(BookWithAuthorsDTO source, Book destination) {
        destination.setAuthors(new HashSet<>(authorRepository.findAllById(source.getAuthorsIds())));
    }

    @Override
    protected void mapSpecificFields(Book source, BookWithAuthorsDTO destination) {
        destination.setAuthorsIds(getIds(source));
    }
    @Override
    protected Set<Long> getIds(Book entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getAuthors())
                ? Collections.emptySet()
                : entity.getAuthors().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}
