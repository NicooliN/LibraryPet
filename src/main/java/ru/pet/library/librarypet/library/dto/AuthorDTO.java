package ru.pet.library.librarypet.library.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.pet.library.librarypet.library.model.Book;

import java.time.LocalDate;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@ToString
public class AuthorDTO
        extends GenericDTO {

    private String authorFio;

    private String birthDate;

    private String description;

    private Set<Long> booksIds;
    private boolean isDeleted;
}
