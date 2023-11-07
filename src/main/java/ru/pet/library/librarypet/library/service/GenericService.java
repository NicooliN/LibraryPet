package ru.pet.library.librarypet.library.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.pet.library.librarypet.library.dto.BookDTO;
import ru.pet.library.librarypet.library.dto.GenericDTO;
import ru.pet.library.librarypet.library.dto.BookDTO;

import ru.pet.library.librarypet.library.exception.MyDeleteException;
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
    public Page<D> listAll(Pageable pageable) {
        Page<E> objects = genericRepository.findAll(pageable);
        List<D> result = genericMapper.toDtos(objects.getContent());
        return new PageImpl<>(result, pageable, objects.getTotalElements());
    }
    public D create(D newObject) {
    newObject.setCreatedBy("ADMIN");
    newObject.setCreatedWhen(LocalDateTime.now());
        return genericMapper.toDto(genericRepository.save(genericMapper.toEntity(newObject)));
    }

    public D update(D newObject) {
        return genericMapper.toDto(genericRepository.save(genericMapper.toEntity(newObject)));
    }

    public void delete(Long id) throws MyDeleteException {
        genericRepository.deleteById(id);
    }

    public void markAsDeleted(GenericModel genericModel) {
        genericModel.setDeleted(true);
        genericModel.setDeletedWhen(LocalDateTime.now());
        genericModel.setDeletedBy(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public void unMarkAsDeleted(GenericModel genericModel) {
        genericModel.setDeleted(false);
        genericModel.setDeletedWhen(null);
        genericModel.setDeletedBy(null);
    }
}
