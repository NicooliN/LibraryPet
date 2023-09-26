package ru.pet.library.librarypet.library.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookWithAuthorsDTO
        extends BookDTO {

    private Set<AuthorDTO> authors;
}
