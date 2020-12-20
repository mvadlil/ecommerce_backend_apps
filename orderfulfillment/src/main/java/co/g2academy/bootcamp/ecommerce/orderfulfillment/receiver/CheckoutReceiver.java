package co.g2academy.bootcamp.ecommerce.orderfulfillment.receiver;


import co.g2academy.bootcamp.ecommerce.orderfulfillment.entity.Order;
import co.g2academy.bootcamp.ecommerce.orderfulfillment.model.Cart;
import co.g2academy.bootcamp.ecommerce.orderfulfillment.model.Converter;
import co.g2academy.bootcamp.ecommerce.orderfulfillment.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RabbitListener(queues = "ecommerceq")
@Component
public class CheckoutReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(CheckoutReceiver.class);

    @Autowired
    private OrderRepository orderRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Converter converter = new Converter();

    @RabbitHandler
    public void receive(byte[] message) {
        String messageBody = new String(message);
        try {
            Cart checkout = objectMapper.readValue(messageBody, Cart.class);
            Order order = converter.convert(checkout);
            orderRepository.save(order);
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
        }
    }


}
