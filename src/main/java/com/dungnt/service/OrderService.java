package com.dungnt.service;

import com.dungnt.entity.Order;
import com.dungnt.util.CommonUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class OrderService {
    @Inject
    CommonUtils utils;

    @Transactional
    public Order createLinkOrder() {
        Order order = Order.builder().orderId(utils.generateULID()).type("link").status(0).build();
        order.persist();
        return order;
    }

    @Transactional
    public Order createPayOrder(String orderId, Integer amount) {
        Order order = Order.builder().orderId(orderId).type("pay").status(0).amount(amount).build();
        order.persist();
        return order;
    }

    @Transactional
    public Order createRefundOrder(String orderId, Integer amount) {
        Order order = Order.builder().orderId(orderId).type("refund").status(0).amount(amount).build();
        order.persist();
        return order;
    }

    public List<Order> getPayOrders(String type) {
        return Order.find("type = ?1", type).list();
    }

    public List<Order> getOrders() {
        return Order.findAll().list();
    }

    @Transactional
    public void updateOrder(String orderId, Integer status) {
        Order order = Order.find("orderId = ?1", orderId).firstResult();
        order.setStatus(status);
        order.persist();
    }
}
