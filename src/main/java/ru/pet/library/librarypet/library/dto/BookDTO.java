package ru.pet.library.librarypet.library.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pet.library.librarypet.library.model.Author;
import ru.pet.library.librarypet.library.model.BookRentInfo;
import ru.pet.library.librarypet.library.model.Genre;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class BookDTO
        extends GenericDTO {

    private String title;

    private Integer publishDate;

    private String publish;

    private Integer pageCount;

    private String storagePlace;

    private String onlineCopy;

    private String description;

    private Integer amount;

    private Genre genre;

    private Set<Long> authorsIds;

    private Set<Long> bookRentInfosIds;
}
