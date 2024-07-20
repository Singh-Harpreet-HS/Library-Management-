package com.example.api.apipractice.services

import com.example.api.apipractice.model.OrderStatus
import com.example.api.apipractice.repository.OrderRepository
import org.springframework.stereotype.Service
import com.example.api.apipractice.model.Orders

@Service
class OrderService(private val orderRepository: OrderRepository) {
    fun placeOrder(order: Orders): Orders {
        return orderRepository.save(order)
    }

    fun updateOrder(id: String, order: Orders): Orders {
        val existingOrder = orderRepository.findById(id)
            .orElseThrow { RuntimeException("Order with ID $id not found") }

        //existingOrder.id = order.id
        existingOrder.userId = order.userId
        existingOrder.bookIds = order.bookIds
        existingOrder.totalAmount = order.totalAmount
        existingOrder.orderDate = order.orderDate
        existingOrder.status = order.status

        return orderRepository.save(existingOrder)
    }

    fun getOrderById(id: String): Orders? {
        return orderRepository.findById(id).orElse(null)
    }

    fun listOrdersByUserId(userId: String): List<Orders> {
        return orderRepository.findByUserId(userId)
    }

    fun cancelOrder(id: String) {
        val order = getOrderById(id)
        if (order != null) {
            order.status = OrderStatus.CANCELLED
            orderRepository.save(order)
        }
    }
}