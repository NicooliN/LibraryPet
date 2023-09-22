package ru.pet.library.librarypet.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.pet.library.librarypet.library.model.GenericModel;
@NoRepositoryBean
public interface GenericRepository <T extends GenericModel>
                extends JpaRepository<T, Long> {

}
