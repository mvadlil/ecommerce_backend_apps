package co.g2academy.bootcamp.ecommerce.orderfulfillment.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class Product implements Serializable {

    private Integer id;

    private String productName;

    private String description;

    private String category;

    private Integer price;

    private Integer stock;

    private User user;

}
