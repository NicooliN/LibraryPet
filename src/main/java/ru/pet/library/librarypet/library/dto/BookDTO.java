package ru.pet.library.librarypet.library.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pet.library.librarypet.library.model.Author;
import ru.pet.library.librarypet.library.model.BookRentInfo;
import ru.pet.library.librarypet.library.model.Genre;

import java.time.LocalDate;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class BookDTO
        extends GenericDTO {

    private String bookTitle;

    private String publishDate;

    private Integer pageCount;

    private Integer amount;

    private String storagePlace;

    private String onlineCopy;
    private String publish;

    private String description;

    private Genre genre;

    private Set<Long> authorsIds;
}
