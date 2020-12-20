package co.g2academy.bootcamp.ecommerce.repository;


import co.g2academy.bootcamp.ecommerce.entity.Product;
import co.g2academy.bootcamp.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    Page<Product> findByCategory(String category, Pageable pageable);
    Page<Product> findByProductNameContaining(String productName, Pageable pageable);

    Product findByIdAndUser(Integer id, User user);
    List<Product> findAll();

}
