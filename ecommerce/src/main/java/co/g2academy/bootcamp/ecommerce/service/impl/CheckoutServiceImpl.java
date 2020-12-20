package co.g2academy.bootcamp.ecommerce.service.impl;

import co.g2academy.bootcamp.ecommerce.AppConfig;
import co.g2academy.bootcamp.ecommerce.entity.Cart;
import co.g2academy.bootcamp.ecommerce.entity.CartItem;
import co.g2academy.bootcamp.ecommerce.entity.Product;
import co.g2academy.bootcamp.ecommerce.entity.User;
import co.g2academy.bootcamp.ecommerce.model.AddToCart;
import co.g2academy.bootcamp.ecommerce.repository.CartRepository;
import co.g2academy.bootcamp.ecommerce.repository.ProductRepository;
import co.g2academy.bootcamp.ecommerce.repository.UserRepository;
import co.g2academy.bootcamp.ecommerce.service.ChekoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CheckoutServiceImpl implements ChekoutService {

    private static final Logger LOG = LoggerFactory.getLogger(CheckoutServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;



    @Override
    @Transactional
    public String addToCart(String userName, AddToCart addToCart) {
        User user = userRepository.findByUserName(userName);
        // get checkout data that has status = Active
        Cart checkout = cartRepository.findByUserAndStatus(user,"ACTIVE");



        if (checkout == null) {
            checkout = new Cart();
            checkout.setUser(user);
            checkout.setStatus("ACTIVE");
            List<CartItem> items = new ArrayList<>();
            checkout.setItems(items);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            checkout.setTransactionDate(formatter.format(date));
        }
        Product product = productRepository.findById(addToCart.getProductId()).get();
        if (product != null && product.getStock() > 0) {
            CartItem item = new CartItem();
            item.setCart(checkout);
            item.setPrice(product.getPrice());
            item.setQuantity(addToCart.getQuantity());
            item.setProduct(product);
            checkout.getItems().add(item);
        }  else {
            return "SORRY, THE STOCK PRODUCT IS EMPTY";
        }
        cartRepository.save(checkout);

        return "OK";
    }


    @Override
    @Transactional
    public void checkout(String userName) {
        User user = userRepository.findByUserName(userName);
        // get checkout data that has status = Active
        Cart checkout = cartRepository.findByUserAndStatus(user,"ACTIVE");

        if (checkout != null) {
            checkout.setStatus("PROCESSED");
            // send status to rabbitmq
            LOG.info("sending message to amqp");
            rabbitTemplate.convertAndSend(AppConfig.QUEUE_NAME, checkout);
            // save to database after changing status
            cartRepository.save(checkout);

            // EXTRA MILES : REDUCE STOCK ON PRODUCT
            // get the product items
            List<CartItem> cartItemList = checkout.getItems();
            for (int i = 0; i < cartItemList.size(); i++) {
                Product product = cartItemList.get(i).getProduct();
                Integer quantity = cartItemList.get(i).getQuantity();
                product.setStock(product.getStock() - quantity);
                productRepository.save(product);
            }

        }
    }

}
