package co.g2academy.bootcamp.ecommerce.orderfulfillment.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private Integer id;

    private String userName;

    private String name;

    private String password;

}
