package ru.pet.library.librarypet.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "users_seq", allocationSize = 1)

public class User extends GenericModel {

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;


    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false,foreignKey = @ForeignKey(name = "FK_USER_ROLES")
    )
    private Role role;

@OneToMany(mappedBy = "user")
    private Set<BookRentInfo> bookRentInfos;


}
