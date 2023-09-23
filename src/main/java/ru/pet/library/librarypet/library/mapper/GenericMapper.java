package ru.pet.library.librarypet.library.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pet.library.librarypet.library.dto.GenericDTO;
import ru.pet.library.librarypet.library.model.GenericModel;

import java.util.List;
import java.util.Objects;

@Component
public abstract class GenericMapper <E extends GenericModel, D extends GenericDTO>
        implements Mapper<E, D> {

    protected final ModelMapper modelMapper;

    private final Class<E> entityClass;

    private final Class<D> dtoClass;

    protected GenericMapper(ModelMapper modelMapper,
                            Class<E> entityClass,
                            Class<D> dtoClass) {
        this.modelMapper = modelMapper;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }
    @Override
    public E toEntity(D dto) {
        return Objects.isNull(dto)
                ? null
                : modelMapper.map(dto, entityClass);
    }

    @Override
    public List<E> toEntities(List<D> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }

    @Override
    public D toDto(E entity) {
        return Objects.isNull(entity)
                ? null
                : modelMapper.map(entity, dtoClass);
    }

    @Override
    public List<D> toDtos(List<E> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
