package co.g2academy.bootcamp.ecommerce.repository;


import co.g2academy.bootcamp.ecommerce.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUserName(String userName);
    List<User> findAll();

}
