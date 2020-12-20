package co.g2academy.bootcamp.ecommerce.orderfulfillment.model;

import co.g2academy.bootcamp.ecommerce.orderfulfillment.entity.Order;
import co.g2academy.bootcamp.ecommerce.orderfulfillment.entity.OrderItem;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ConverterTest {

    private Converter converter = new Converter();

    @Test
    public void convertWithTrueObject() {

        // User
        User user = new User();
        user.setId(1);
        user.setUserName("fadlil@gmail.com");
        user.setName("fadlil");
        user.setPassword("passwordku");

        // Product
        Product product = new Product();
        product.setId(1);
        product.setProductName("samsung s10 8/128GB");
        product.setDescription("the best smartphone ever");
        product.setCategory("handphone");
        product.setPrice(10_000_000);
        product.setStock(10);
        product.setUser(user);

        // CartItem
        CartItem item = new CartItem();
        item.setId(1);
        item.setProduct(product);
        item.setQuantity(1);
        item.setPrice(product.getPrice());

        // Cart
        Cart cart = new Cart();
        cart.setId(1);
        cart.setUser(user);
        List<CartItem> items = new ArrayList<>();
        items.add(item);
        cart.setItems(items);
        cart.setStatus("ACTIVE");
        cart.setTransactionDate("15 DEC 2020");

        // explicit set for
        item.setCart(cart);

        // THE CART IS READY, LETS TRY TO TEST THE CONVERTER
        // 1. convert the cart to Order
        Order actual = converter.convert(cart);

        // 2. create the expected order result

        // create orderItemExpected
        OrderItem orderItemExpected = new OrderItem();
        orderItemExpected.setId(null);
        orderItemExpected.setProductName("samsung s10 8/128GB");
        orderItemExpected.setProductId(1);
        orderItemExpected.setQuantity(1);
        orderItemExpected.setPrice(10_000_000);

        // create Order Expected
        Order expected = new Order();
        expected.setId(null);
        expected.setUserId(1);
        expected.setCartId(1);
        // order date on order and transaction date on cart is generated different date,
        // because maybe the received date is different between order date and transaction date.
        expected.setOrderDate(new Date());
        expected.setStatus("RECEIVED");
        expected.setTotalPrice(orderItemExpected.getPrice() * orderItemExpected.getQuantity());
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItemExpected);
        expected.setOrderItems(orderItemList);

        // Explicit set
        orderItemExpected.setOrder(expected);

        // 3. assert all fields
        assertEquals(expected.getUserId(), actual.getUserId());
        assertEquals(expected.getCartId(), actual.getCartId());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getTotalPrice(), actual.getTotalPrice());
        // there is one left not tested yet, orderItem List
        // there is no assert to test ArrayList
    }


}