package co.g2academy.bootcamp.ecommerce.repository;

import co.g2academy.bootcamp.ecommerce.entity.Product;
import co.g2academy.bootcamp.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CachedProductRepository {


    @Autowired
    private ProductRepository productRepository;

    @Cacheable(value = "findByCategory")
    public Page<Product> findByCategory(String category,
                                        Integer page,
                                        String sort) {
        return productRepository.findByCategory(category, buildPageable(page, sort));
    }

    @Cacheable(value = "findByProductNameContaining")
    public Page<Product> findByProductNameContaining(String query,
                                              Integer page,
                                              String sort) {
        return productRepository.findByProductNameContaining(query, buildPageable(page, sort));
    }

    @Cacheable(value = "findByIdAndUser")
    public Product findByIdAndUser(Integer id, User productOwner) {
        Product product = productRepository.findByIdAndUser(id, productOwner);
        if (product != null){
            return product;
        }
        return null;
    }

    @Cacheable(value = "findAll")
    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        if (products != null){
            return products;
        }
        return null;
    }

    private Pageable buildPageable(Integer page, String sort) {
        Sort.Order order = null;
        if ("PRICE_ASC".equals(sort)) {
            order = new Sort.Order(Sort.Direction.ASC, "price");
        } else if ("PRICE_DSC".equals(sort)) {
            order = new Sort.Order(Sort.Direction.DESC, "price");
        } else if ("TITLE".equals(sort)) {
            order = new Sort.Order(Sort.Direction.ASC, "productName");
        }
        return PageRequest.of(page, 20, Sort.by(order));
    }


}
