package co.g2academy.bootcamp.ecommerce.orderfulfillment.repository;

import co.g2academy.bootcamp.ecommerce.orderfulfillment.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {



}
