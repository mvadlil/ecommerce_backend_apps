package co.g2academy.bootcamp.ecommerce.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "T_USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "USERNAME", unique = true)
    private String userName;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PASSWORD")
    private String password;


}
