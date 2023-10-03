package ru.pet.library.librarypet.library.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pet.library.librarypet.library.model.BookRentInfo;
import ru.pet.library.librarypet.library.model.Role;

import java.time.LocalDate;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class UserDTO
        extends GenericDTO{
    private String login;
    private String password;
    private String email;
    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String address;
    private Role role;
    private Set<Long> bookRentInfosIds;
}
