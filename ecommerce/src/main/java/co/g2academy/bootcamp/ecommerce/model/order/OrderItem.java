package co.g2academy.bootcamp.ecommerce.model.order;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class OrderItem implements Serializable {

    private Integer id;

    private String productName;

    private Integer productId;

    private Integer quantity;

    private Integer price;

    private Order order;


}
