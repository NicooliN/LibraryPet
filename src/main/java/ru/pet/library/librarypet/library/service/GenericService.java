package ru.pet.library.librarypet.library.service;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.pet.library.librarypet.library.dto.BookDTO;
import ru.pet.library.librarypet.library.dto.GenericDTO;
import ru.pet.library.librarypet.library.dto.BookDTO;

import ru.pet.library.librarypet.library.mapper.GenericMapper;
import ru.pet.library.librarypet.library.model.GenericModel;
import ru.pet.library.librarypet.library.repository.GenericRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public abstract class GenericService<E extends GenericModel, D extends GenericDTO> {

    protected final GenericRepository<E> genericRepository;
    protected final GenericMapper<E, D> genericMapper;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected GenericService(GenericRepository<E> genericRepository,
                             GenericMapper<E,D> genericMapper) {
        this.genericMapper = genericMapper;
        this.genericRepository = genericRepository;
    }

    public List<D> listAll() {
        return genericMapper.toDtos(genericRepository.findAll());
    }
    public D getOne(final Long id) {
        return genericMapper.toDto(genericRepository.findById(id).orElseThrow(() -> new NotFoundException("Данных по " + id + " нету")));
    }

    public D create(D newObject) {
    newObject.setCreatedBy("ADMIN");
    newObject.setCreatedWhen(LocalDateTime.now());
        return genericMapper.toDto(genericRepository.save(genericMapper.toEntity(newObject)));
    }

    public D update(D newObject) {
        return genericMapper.toDto(genericRepository.save(genericMapper.toEntity(newObject)));
    }

    public void delete(Long id) {
        genericRepository.deleteById(id);
    }
}
