package com.example.api.apipractice.controllers

import com.example.api.apipractice.model.Orders
import com.example.api.apipractice.services.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(private val orderService: OrderService) {
    @PostMapping
    fun placeOrder(@RequestBody order: Orders): ResponseEntity<Orders> {
        return ResponseEntity.ok(orderService.placeOrder(order))
    }

    @PutMapping("/{id}")
    fun updateOrder(@PathVariable id: String, @RequestBody order: Orders): ResponseEntity<Orders> {
        return ResponseEntity.ok(orderService.updateOrder(id,order))
    }

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: String): ResponseEntity<Orders> {
        return if (orderService.getOrderById(id) != null) {
            ResponseEntity.ok(orderService.getOrderById(id))
        } else {
            ResponseEntity.notFound().header("Message", "Order with ID $id not found").build()
        }
    }

    @GetMapping("/user/{userId}")
    fun listOrdersByUserId(@PathVariable userId: String): ResponseEntity<List<Orders>> {
        return ResponseEntity.ok(orderService.listOrdersByUserId(userId))
    }

    @PutMapping("/{id}/cancel")
    fun cancelOrder(@PathVariable id: String): ResponseEntity<Void> {
        orderService.cancelOrder(id)
        return ResponseEntity.noContent().build()
    }
}