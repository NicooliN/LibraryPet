package ru.pet.library.librarypet.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "author")
@Getter @Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "authors_seq", allocationSize = 1)
public class Author extends GenericModel{


    @Column(name = "fio")
    private String fio;

    @Column(name = "description")
    private String description;

    @Column(name = "bith_date")
    private LocalDate bithDate;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

}
