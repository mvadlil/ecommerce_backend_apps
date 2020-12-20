package co.g2academy.bootcamp.ecommerce.model;

import lombok.Data;

@Data
public class Register {

    private String userName;
    private String name;
    private String password;
    private String confirmPassword;

}
