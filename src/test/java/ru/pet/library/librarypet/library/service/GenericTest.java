package ru.pet.library.librarypet.library.service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.pet.library.librarypet.library.dto.GenericDTO;
import ru.pet.library.librarypet.library.exception.MyDeleteException;
import ru.pet.library.librarypet.library.model.GenericModel;
import ru.pet.library.librarypet.library.service.userdetails.CustomUserDetails;

public abstract class GenericTest <E extends GenericModel, D extends GenericDTO> {

    protected GenericService<E, D> service;

    @BeforeEach
    void init() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(CustomUserDetails.builder().username("USER"),
                null,
                null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    protected  abstract void getAll();
    protected  abstract void getOne();
    protected  abstract void create();

    protected  abstract void update();

    protected  abstract void delete() throws MyDeleteException;

    protected  abstract void restore();
}
