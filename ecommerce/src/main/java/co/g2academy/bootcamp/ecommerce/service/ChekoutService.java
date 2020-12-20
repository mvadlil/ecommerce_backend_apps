package co.g2academy.bootcamp.ecommerce.service;

import co.g2academy.bootcamp.ecommerce.entity.Cart;
import co.g2academy.bootcamp.ecommerce.model.AddToCart;

public interface ChekoutService {

    public String addToCart(String userName, AddToCart addToCart);

    public void checkout(String userName);

}
