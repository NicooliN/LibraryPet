package ru.pet.library.librarypet.library.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pet.library.librarypet.library.model.Book;

import java.time.LocalDate;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class AuthorDTO
        extends GenericDTO {

    private String fio;

    private String description;

    private LocalDate birthDate;

    private Set<Long> booksIds;
}
