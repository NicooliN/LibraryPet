package ru.pet.library.librarypet.library.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import java.util.UUID;


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
        roleDTO.setId(1L);
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

}
