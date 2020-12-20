package co.g2academy.bootcamp.ecommerce.orderfulfillment.controller;


import co.g2academy.bootcamp.ecommerce.orderfulfillment.entity.Order;
import co.g2academy.bootcamp.ecommerce.orderfulfillment.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;


    @PostMapping("/order/{orderId}")
    public String setStatusToShipped(@PathVariable Integer orderId) {
        Order order = orderRepository.findById(orderId).get();
        if (order != null) {
            order.setStatus("SHIPPED");
            orderRepository.save(order);
        }
        return "OK";
    }

}
