package ru.pet.library.librarypet.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "books")
@Getter @Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "books_seq", allocationSize = 1)
public class Book extends GenericModel{


    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "publish_date")
    private Integer publishDate;

    @Column(name = "publish")
    private String publish;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "storage_place")
    private String storagePlace;

    @Column(name = "online_copy_path")
    private String onlineCopy;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private Integer amount;

    @Column(name =  "genre", nullable = false)
    @Enumerated
    private Genre genre;

    @ManyToMany
    @JoinTable(name ="book_author",
    joinColumns = @JoinColumn(name = "book_id", foreignKey =  @ForeignKey(name = "FK_BOOKS_AUTHORS")),
    inverseJoinColumns = @JoinColumn(name = "author_id"), inverseForeignKey = @ForeignKey(name = "FK_AUTHORS_BOOKS"))
    private Set<Author> authors;

    @OneToMany(mappedBy = "book")
    private Set<BookRentInfo> bookRentInfos;
}
