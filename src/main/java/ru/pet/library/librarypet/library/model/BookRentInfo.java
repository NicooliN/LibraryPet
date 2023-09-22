package ru.pet.library.librarypet.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "book_rent_info")
@NoArgsConstructor
@Getter@Setter
@SequenceGenerator(name = "default_generator", sequenceName = "book_rent_info_seq", allocationSize = 1)

public class BookRentInfo  extends GenericModel{

    @ManyToOne
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_RENT_BOOK_INFO_BOOKS"))
  private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_RENT_BOOK_INFO_USERS"))
    private User user;

    @Column(name = "rent_date")
    private LocalDateTime rentDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "rent_period")
    private Integer rentPeriod;

    @Column(name = "returned")
    private Boolean returned;
}
