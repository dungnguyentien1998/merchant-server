package com.dungnt.service;

import com.dungnt.entity.Order;
import com.dungnt.util.CommonUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class OrderService {
    @Inject
    CommonUtils utils;

    @Transactional
    public Order createOrder() {
        Order order = Order.builder().orderId(utils.generateULID()).type("link").status(0).build();
        order.persist();
        return order;
    }
}
