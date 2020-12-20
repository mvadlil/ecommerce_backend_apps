package co.g2academy.bootcamp.ecommerce.repository;

import co.g2academy.bootcamp.ecommerce.entity.Cart;
import co.g2academy.bootcamp.ecommerce.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {

    Cart findByUserAndStatus(User user, String status);
    Cart findByUserName(String userName);

}
