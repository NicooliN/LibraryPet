package ru.pet.library.librarypet.library.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pet.library.librarypet.library.model.GenericModel;
import ru.pet.library.librarypet.library.dto.UserDTO;
import ru.pet.library.librarypet.library.model.User;
import ru.pet.library.librarypet.library.repository.BookRentInfoRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper
        extends GenericMapper<User, UserDTO>{

    private final BookRentInfoRepository bookRentInfoRepository;

    protected UserMapper(BookRentInfoRepository bookRentInfoRepository, ModelMapper modelMapper) {
        super(modelMapper, User.class, UserDTO.class);
        this.bookRentInfoRepository = bookRentInfoRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(m -> m.skip(UserDTO::setBookRentInfosIds)).setPostConverter(toDTOConverter());
        modelMapper.createTypeMap(UserDTO.class, User.class)
                .addMappings(m -> m.skip(User::setBookRentInfos)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(UserDTO source, User destination) {
        if (!Objects.isNull(source.getBookRentInfosIds())) {
            destination.setBookRentInfos(new HashSet<>(bookRentInfoRepository.findAllById(source.getBookRentInfosIds())));
        } else destination.setBookRentInfos(Collections.emptySet());
    }

    @Override
    protected void mapSpecificFields(User source, UserDTO destination) {
        destination.setBookRentInfosIds(getIds(source));
    }

    @Override
    protected Set<Long> getIds(User entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getBookRentInfos())
                ? null
                : entity.getBookRentInfos().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}
