package com.example.fastfoodpos.data.repository.impl

import com.example.fastfoodpos.data.network.FastFoodApi
import com.example.fastfoodpos.data.room.DAO.MenuDao
import com.example.fastfoodpos.data.room.DAO.OrderDao
import com.example.fastfoodpos.domain.model.FoodItem
import com.example.fastfoodpos.domain.model.Order
import com.example.fastfoodpos.domain.repository.FastFoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FastFoodRepositoryImpl(
    private val fastFoodApi: FastFoodApi,
    private val menuDao: MenuDao,
    private val orderDao: OrderDao
) : FastFoodRepository {
    override suspend fun fetchFoodItems(): List<FoodItem> {
        println("FastFoodRepositoryImpl: fetchFoodItems() called")
        return try {
            val items = fastFoodApi.fetchFoodItems()
            println("FastFoodRepositoryImpl: Food items fetched from API: $items")
            items
        } catch (e: Exception) {
            println("FastFoodRepositoryImpl: Error fetching food items from API: ${e.message}")
            emptyList()
        }
    }

    override suspend fun saveOrderToLocal(order: Order) {
        try {
            orderDao.insertOrder(
                com.example.fastfoodpos.data.room.Entity.OrderEntity(
                    id = 0,
                    orderDate = order.orderDate,
                    totalPrice = order.totalPrice,
                    items = order.items
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun fetchOrdersFromLocal(): Flow<List<Order>> {
        return orderDao.getAllOrders().map { list ->
            list.map {
                Order(
                    id = it.id,
                    orderDate = it.orderDate,
                    totalPrice = it.totalPrice,
                    items = it.items
                )
            }
        }
    }
}