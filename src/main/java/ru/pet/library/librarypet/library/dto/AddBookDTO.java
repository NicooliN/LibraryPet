package ru.pet.library.librarypet.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AddBookDTO {
    Long bookId;
    Long authorId;
}
