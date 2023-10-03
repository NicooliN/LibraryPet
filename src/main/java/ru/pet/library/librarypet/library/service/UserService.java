//package ru.pet.library.librarypet.library.service;
//
//
//
//import ru.pet.library.librarypet.library.dto.RoleDTO;
//import ru.pet.library.librarypet.library.dto.UserDTO;
//import ru.pet.library.librarypet.library.mapper.UserMapper;
//import ru.pet.library.librarypet.library.model.User;
//import ru.pet.library.librarypet.library.repository.UserRepository;
//
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//
//import java.time.LocalDateTime;
//
//
//import static ru.pet.library.librarypet.library.constants.UserRolesConstants.ADMIN;
//
//@Service
//public class UserService
//      extends GenericService<User, UserDTO> {
//
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final UserMapper userMapper;
//    private final UserRepository userRepository;
//
//    protected UserService(
//                          BCryptPasswordEncoder bCryptPasswordEncoder,
//                          UserMapper userMapper, UserRepository userRepository) {
//        super(userRepository, userMapper);
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.userMapper = userMapper;
//        this.userRepository = userRepository;
//    }
//
//    public Boolean checkPassword(String password, UserDetails userDetails) {
//        return bCryptPasswordEncoder.matches(password, userDetails.getPassword());
//    }
//
//    @Override
//    public UserDTO create(UserDTO object) {
//        RoleDTO roleDTO = new RoleDTO();
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
//        if (ADMIN.equalsIgnoreCase(userName)) {
//            roleDTO.setId(2L);//библиотекарь
//        }
//        else {
//            roleDTO.setId(1L);//пользователь
//        }
//        object.setRole(roleDTO);
//        object.setCreatedBy("REGISTRATION FORM");
//        object.setCreatedWhen(LocalDateTime.now());
//        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
//        return userMapper.toDto(userRepository.save(userMapper.toEntity(object)));
//    }
//
//
//
//
//
//
//}
