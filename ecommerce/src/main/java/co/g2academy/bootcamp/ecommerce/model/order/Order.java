package co.g2academy.bootcamp.ecommerce.model.order;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Order implements Serializable {

    private Integer id;

    private Integer userId;

    private Integer cartId;

    private Date orderDate;

    private String status;

    private Integer totalPrice;

    private List<OrderItem> orderItems;

}
