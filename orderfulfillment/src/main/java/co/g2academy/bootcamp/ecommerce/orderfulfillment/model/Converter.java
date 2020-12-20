package co.g2academy.bootcamp.ecommerce.orderfulfillment.model;

import co.g2academy.bootcamp.ecommerce.orderfulfillment.entity.Order;
import co.g2academy.bootcamp.ecommerce.orderfulfillment.entity.OrderItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converter {


    public Order convert(Cart checkout) {
        Order order = new Order();
        order.setUserId(checkout.getUser().getId());
        order.setCartId(checkout.getId());
        order.setOrderDate(new Date());
        order.setStatus("RECEIVED");
        List<OrderItem> orderItems = new ArrayList<>();

        Integer totalPrice = 0;
        for (CartItem item : checkout.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductName(item.getProduct().getProductName());
            orderItem.setProductId(item.getProduct().getId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());
            orderItem.setOrder(order);
            totalPrice += item.getPrice() * item.getQuantity();
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);
        return order;
    }


}
