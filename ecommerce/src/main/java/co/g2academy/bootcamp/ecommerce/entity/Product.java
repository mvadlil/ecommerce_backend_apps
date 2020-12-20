package co.g2academy.bootcamp.ecommerce.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "T_PRODUCT")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CATEGORY", length = 100)
    private String category;

    @Column(name = "PRICE")
    private Integer price;

    @Column(name = "STOCK")
    private Integer stock;

    @ManyToOne(optional = false)
    private User user;

}
