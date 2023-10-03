package ru.pet.library.librarypet.library.repository;

import org.springframework.stereotype.Repository;
import ru.pet.library.librarypet.library.model.User;

@Repository
public interface UserRepository
        extends GenericRepository<User>{

    User findUserByLogin(String login);

}
