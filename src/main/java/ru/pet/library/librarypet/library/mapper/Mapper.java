package ru.pet.library.librarypet.library.mapper;

import ru.pet.library.librarypet.library.dto.GenericDTO;
import ru.pet.library.librarypet.library.model.GenericModel;

import java.util.List;

public interface Mapper <E extends GenericModel, D extends GenericDTO> {

    E toEntity(D dto);

    D toDto(E entity);

    List<D> toDtos(List<E> entities);

    List<E> toEntities(List<D> dtos);
}
