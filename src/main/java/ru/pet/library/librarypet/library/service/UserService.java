package ru.pet.library.librarypet.library.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.webjars.NotFoundException;
import ru.pet.library.librarypet.library.constants.MailConstants;
import ru.pet.library.librarypet.library.dto.RoleDTO;
import ru.pet.library.librarypet.library.dto.UserDTO;
import ru.pet.library.librarypet.library.mapper.UserMapper;
import ru.pet.library.librarypet.library.model.User;
import ru.pet.library.librarypet.library.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pet.library.librarypet.library.utils.MailUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static ru.pet.library.librarypet.library.constants.UserRolesConstants.ADMIN;


@Service
public class UserService
      extends GenericService<User, UserDTO> {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;

    protected UserService(
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          UserMapper userMapper, UserRepository userRepository,
                            JavaMailSender javaMailSender) {
        super(userRepository, userMapper);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;

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
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (ADMIN.equalsIgnoreCase(userName)) {
            roleDTO.setId(2L);//библиотекарь
        }
        else {
            roleDTO.setId(1L);//пользователь
        }
        object.setRole(roleDTO);
        object.setCreatedBy("REGISTRATION FORM");
        object.setCreatedWhen(LocalDateTime.now());
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        return genericMapper.toDto(genericRepository.save(genericMapper.toEntity(object)));
    }

    public void sendChangePasswordEmail(final UserDTO userDTO){
        UUID uuid = UUID.randomUUID();
        userDTO.setChangePasswordToken(uuid.toString());
        update(userDTO);
        SimpleMailMessage mailMessage = MailUtils.createEmailMessage(userDTO.getEmail(),
                MailConstants.MAIL_SUBJECT_FOR_REMEMBER_PASSWORD,
                MailConstants.MAIL_MESSAGE_FOR_REMEMBER_PASSWORD);
        javaMailSender.send(mailMessage);
    }

    public void changePassword(final String uuid,
                               final String password) {
        UserDTO userDTO = genericMapper.toDto(((UserRepository) genericRepository).findUserByChangePasswordToken(uuid));
        userDTO.setChangePasswordToken(null);
        userDTO.setPassword(bCryptPasswordEncoder.encode(password));
        update(userDTO);
    }

    public Page<UserDTO> findUsers(UserDTO userDTO,
                                   Pageable pageable) {
        Page<User> users = ((UserRepository) genericRepository).searchUsers(userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getLogin(),
                pageable);
        List<UserDTO> result = genericMapper.toDtos(users.getContent());
        return new PageImpl<>(result, pageable, users.getTotalElements());
    }

    public List<String> getUserEmailsWithDelayedRentDate() {
        return ((UserRepository) genericRepository).getDelayedEmails();
    }

    @Override
    public void delete(Long id) {
        User user = genericRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователя с заданным ID=" + id + " не существует"));
        markAsDeleted(user);
        genericRepository.save(user);
    }

    public void restore(Long objectId) {
        User user = genericRepository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Пользователя с заданным ID=" + objectId + " не существует"));
        unMarkAsDeleted(user);
        genericRepository.save(user);
    }
}
