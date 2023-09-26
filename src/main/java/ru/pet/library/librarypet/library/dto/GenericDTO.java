package ru.pet.library.librarypet.library.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public abstract class GenericDTO {

    private Long id;

    private String createdBy;

    private LocalDateTime createdWhen;
}
