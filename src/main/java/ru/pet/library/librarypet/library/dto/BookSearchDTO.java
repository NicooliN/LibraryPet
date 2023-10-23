package ru.pet.library.librarypet.library.dto;

import lombok.Getter;
import lombok.Setter;
import ru.pet.library.librarypet.library.model.Genre;

@Getter @Setter
public class BookSearchDTO {
    private  String bookTitle;
    private String authorFio;
    private Genre genre;
}
