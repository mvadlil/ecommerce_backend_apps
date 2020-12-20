package co.g2academy.bootcamp.ecommerce.repository;


import co.g2academy.bootcamp.ecommerce.entity.Product;
import co.g2academy.bootcamp.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CachedUserRepository {

    @Autowired
    private UserRepository userRepository;


    @Cacheable(value = "findByUsername")
    public User findByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user != null){
            return user;
        }
        return null;
    }

}
