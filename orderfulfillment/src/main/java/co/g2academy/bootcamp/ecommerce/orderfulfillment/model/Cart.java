package co.g2academy.bootcamp.ecommerce.orderfulfillment.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Cart implements Serializable {

    private Integer id;

    private User user;

    private List<CartItem> items;

    private String status;

    private String transactionDate;

}
