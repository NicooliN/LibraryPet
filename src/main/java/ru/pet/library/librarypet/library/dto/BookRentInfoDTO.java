package ru.pet.library.librarypet.library.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookRentInfoDTO
      extends GenericDTO {
    
    private BookDTO bookDTO;
//    private UserDTO user;
    private LocalDateTime rentDate;
    private LocalDateTime returnDate;
    private Boolean returned;
    private Integer rentPeriod;
    private Long bookId;
    private Long userId;
    
}
