package com.example.api.apipractice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

@Document(collection = "orders")
data class Orders(
    @Id var id: String? = null,
    var userId: String?,
    var bookIds: List<String>,
    var totalAmount: BigDecimal?,
    var orderDate: LocalDateTime = LocalDateTime.now(),
    var status: OrderStatus = OrderStatus.PENDING
)

enum class OrderStatus {
    PENDING,
    COMPLETED,
    CANCELLED
}
