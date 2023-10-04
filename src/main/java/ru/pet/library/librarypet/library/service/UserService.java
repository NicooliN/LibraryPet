package ru.pet.library.librarypet.library.service;



import ru.pet.library.librarypet.library.dto.RoleDTO;
import ru.pet.library.librarypet.library.dto.UserDTO;
import ru.pet.library.librarypet.library.mapper.UserMapper;
import ru.pet.library.librarypet.library.model.User;
import ru.pet.library.librarypet.library.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;


import static ru.pet.library.librarypet.library.constants.UserRolesConstants.ADMIN;

@Service
public class UserService
      extends GenericService<User, UserDTO> {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    protected UserService(
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          UserMapper userMapper, UserRepository userRepository) {
        super(userRepository, userMapper);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    public UserDTO getUserByLogin(final String login) {
        return genericMapper.toDto(((UserRepository) genericRepository).findUserByLogin(login));
    }

    public UserDTO getUserByEmail(final String email) {
        return genericMapper.toDto(((UserRepository) genericRepository).findUserByEmail(email));
    }

    @Override
    public UserDTO create(UserDTO object) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        object.setRole(roleDTO);
        object.setCreatedBy("REGISTRATION FORM");
        object.setCreatedWhen(LocalDateTime.now());
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        return genericMapper.toDto(genericRepository.save(genericMapper.toEntity(object)));
    }
}
