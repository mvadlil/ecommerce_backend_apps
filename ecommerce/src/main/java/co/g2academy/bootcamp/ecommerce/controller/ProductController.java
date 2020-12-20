package co.g2academy.bootcamp.ecommerce.controller;

import co.g2academy.bootcamp.ecommerce.entity.Product;
import co.g2academy.bootcamp.ecommerce.entity.User;
import co.g2academy.bootcamp.ecommerce.repository.CachedProductRepository;
import co.g2academy.bootcamp.ecommerce.repository.CachedUserRepository;
import co.g2academy.bootcamp.ecommerce.repository.ProductRepository;
import co.g2academy.bootcamp.ecommerce.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CachedProductRepository cachedProductRepository;

    @Autowired
    private CachedUserRepository cachedUserRepository;

//======================================================================================================================
//    THESE API NEED PRINCIPAL, USER LOGIN IS NEEDED
//======================================================================================================================

    @PostMapping("/product")
    public String save(@RequestBody Product newProduct, Principal principal) {
        newProduct.setUser(userRepository.findByUserName(getUserName(principal)));
        productRepository.save(newProduct);
        return "PRODUCT UPLOADED";
    }

    // this API Cache implemented
    @GetMapping("/products")
    public List<Product> getProducts(Principal principal) {
        // Get the productOwner
        User productOwner = cachedUserRepository.findByUserName(getUserName(principal));
        // find all Product
        List<Product> allProduct = cachedProductRepository.findAll();
        // create new buffer list to load product user
        List<Product> listOfProductUser = new ArrayList<>();
        // filter product
        for (int i = 0; i < allProduct.size(); i++) {
            // filter product get only with userName same as productOwner userName
            if (allProduct.get(i).getUser().getUserName().equals(productOwner.getUserName())) {
                listOfProductUser.add(allProduct.get(i));
            }
        }
        return listOfProductUser;
    }

    // this API Cache implemented
    @GetMapping("/product/id/{id}")
    public Product getProduct(@PathVariable Integer id, Principal principal) {
        // Get the productOwner
        User productOwner = cachedUserRepository.findByUserName(getUserName(principal));
        if (productOwner == null) {
            return null;
        }
        // Search product by findByIdAndUser
        Product productUploaded = cachedProductRepository.findByIdAndUser(id, productOwner);
        if (productUploaded != null) {
            return productUploaded;
        }
        return null;
    }

    @PutMapping("/product/{id}")
    public String update(@RequestBody Product updateProduct, @PathVariable Integer id ,Principal principal) {
        // Get the productOwner
        User productOwner = userRepository.findByUserName(getUserName(principal));
        if (productOwner == null) {
            return "USER NOT FOUND!";
        }
        // Set updateProduct
        Product productUploaded = productRepository.findByIdAndUser(id, productOwner);
        if (productUploaded != null) {
            productUploaded.setProductName(updateProduct.getProductName());
            productUploaded.setCategory(updateProduct.getCategory());
            productUploaded.setDescription(updateProduct.getDescription());
            productUploaded.setPrice(updateProduct.getPrice());
            productUploaded.setStock(updateProduct.getStock());
            productRepository.save(productUploaded);
            return "PRODUCT HAS BEEN UPDATED!";
        }
        return "PRODUCT UPDATE FAILED!";
    }

    @PutMapping("/product/stock/{id}")
    public String updateStock(@RequestBody Product updateProduct, @PathVariable Integer id ,Principal principal) {
        // Get the productOwner
        User productOwner = userRepository.findByUserName(getUserName(principal));
        if (productOwner == null) {
            return "USER NOT FOUND!";
        }
        // Set updateProduct
        Product productUploaded = productRepository.findByIdAndUser(id, productOwner);
        if (productUploaded != null) {
            productUploaded.setStock(updateProduct.getStock());
            productRepository.save(productUploaded);
            return "STOCK HAS BEEN UPDATED!";
        }
        return "STOCK UPDATE FAILED!";
    }

    @DeleteMapping("/product/{id}")
    public String delete(@PathVariable Integer id, Principal principal) {
        // Get the productOwner
        User productOwner = userRepository.findByUserName(getUserName(principal));
        if (productOwner == null) {
            return "USER NOT FOUND!";
        }
        // Delete Product
        Product product = productRepository.findByIdAndUser(id, productOwner);
        if ( product != null ) {
            productRepository.delete(product);
            return "PRODUCT HAS BEEN DELETED";
        } else {
            return "DELETING PRODUCT HAS BEEN FAILED";
        }
    }

    // METHOD TO GET USERNAME OF USER FROM PRINCIPAL
    private String getUserName(Principal principal) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        Claims user = (Claims) token.getPrincipal();
        return user.getSubject();
    }

//======================================================================================================================
//    NO PRINCIPAL NEEDED
//======================================================================================================================

    // this API Cache implemented
    @GetMapping("/product")
    public ResponseEntity<Map<String, Object>> getProductByCategory(
            @RequestParam String category,
            @RequestParam Integer page,
            @RequestParam String sort){
        return buildResponseEntity(cachedProductRepository.findByCategory(category, page, sort));
    }

    // this API Cache implemented
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getProductBySearchQuery(
            @RequestParam String query,
            @RequestParam Integer page,
            @RequestParam String sort) {
        return buildResponseEntity(cachedProductRepository.findByProductNameContaining(query, page, sort));
    }

    private ResponseEntity<Map<String, Object>> buildResponseEntity(Page<Product> productPage) {
        Map<String, Object> response = new HashMap();
        response.put("products", productPage.getContent());
        response.put("currentPage", productPage.getNumber());
        response.put("totalItems", productPage.getTotalElements());
        response.put("totalPages", productPage.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
