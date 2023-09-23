package ru.pet.library.librarypet.library.controller;

import ru.pet.library.librarypet.library.dto.GenericDTO;
import ru.pet.library.librarypet.library.model.GenericModel;
import ru.pet.library.librarypet.library.service.GenericService;

public class GenericController <E extends GenericModel, D extends GenericDTO> {
    private  final GenericService<E, D> genericService;

    public GenericController(GenericService<E, D> genericService) {
        this.genericService = genericService;
    }


}
