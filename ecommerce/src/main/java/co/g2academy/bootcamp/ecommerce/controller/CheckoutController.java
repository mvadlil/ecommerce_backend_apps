package co.g2academy.bootcamp.ecommerce.controller;


import co.g2academy.bootcamp.ecommerce.model.AddToCart;
import co.g2academy.bootcamp.ecommerce.service.ChekoutService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class CheckoutController {

    @Autowired
    private ChekoutService checkoutService;


    @PostMapping("/add-to-cart")
    public String addToCart(@RequestBody AddToCart addToCart, Principal principal) {
        String service = checkoutService.addToCart(getUserName(principal), addToCart);
        return service;
    }

    @PostMapping("/checkout")
    public String checkout(Principal principal) {
        checkoutService.checkout(getUserName(principal));
        return "OK";
    }

    private String getUserName(Principal principal) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        Claims user = (Claims) token.getPrincipal();
        return user.getSubject();
    }


}
