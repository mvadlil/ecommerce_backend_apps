package co.g2academy.bootcamp.ecommerce.orderfulfillment.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class CartItem implements Serializable {

    private Integer id;

    private Cart cart;

    private Product product;

    private Integer quantity;

    private Integer price;

}
