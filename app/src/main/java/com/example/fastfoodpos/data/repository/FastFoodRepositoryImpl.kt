package com.example.fastfoodpos.data.repository

import com.example.fastfoodpos.data.room.DAO.MenuDao
import com.example.fastfoodpos.data.room.DAO.OrderDao
import com.example.fastfoodpos.data.room.Entity.FoodItemEntity
import com.example.fastfoodpos.domain.model.FoodItem
import com.example.fastfoodpos.domain.model.Order
import com.example.fastfoodpos.domain.repository.FastFoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FastFoodRepositoryImpl @Inject constructor(
    private val menuDao: MenuDao,
    private val orderDao: OrderDao
) : FastFoodRepository {

    override fun fetchFoodItems(): Flow<List<FoodItem>> = flow {
        emit(withContext(Dispatchers.IO) {
            menuDao.getAllFoodItems().map {
                FoodItem(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    imageResource = it.imageResource,
                    quantity = it.quantity
                )
            }
        })
    }.catch { e ->
        e.printStackTrace()
        emit(emptyList())
    }

    override suspend fun insertFoodItem(foodItem: FoodItem) {
        withContext(Dispatchers.IO) {
            menuDao.insertFoodItem(
                FoodItemEntity(
                    id = foodItem.id,
                    name = foodItem.name,
                    price = foodItem.price,
                    imageResource = foodItem.imageResource,
                    quantity = foodItem.quantity
                )
            )
        }
    }
    override suspend fun insertFoodItems(foodItems: List<FoodItem>) {
        withContext(Dispatchers.IO) {
            menuDao.insertFoodItems(foodItems.map {
                FoodItemEntity(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    imageResource = it.imageResource,
                    quantity = it.quantity
                )
            })
        }
    }

    override suspend fun saveOrderToLocal(order: Order) {
        try {
            withContext(Dispatchers.IO) {
                orderDao.insertOrder(
                    com.example.fastfoodpos.data.room.Entity.OrderEntity(
                        id = 0,
                        orderDate = order.orderDate,
                        totalPrice = order.totalPrice,
                        items = order.items
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun fetchOrdersFromLocal(): Flow<List<Order>> = flow {
        try {
            orderDao.getAllOrders().collect { orderEntities ->
                val orders = orderEntities.map { orderEntity ->
                    Order(
                        id = orderEntity.id,
                        orderDate = orderEntity.orderDate,
                        totalPrice = orderEntity.totalPrice,
                        items = orderEntity.items
                    )
                }
                emit(orders)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }

    override suspend fun getFoodItemById(itemId: Int): FoodItem? {
        return withContext(Dispatchers.IO) {
            menuDao.getFoodItemById(itemId)?.let {
                FoodItem(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    imageResource = it.imageResource,
                    quantity = it.quantity
                )
            }
        }
    }
    override suspend fun updateFoodItem(foodItem: FoodItem) {
        withContext(Dispatchers.IO) {
            menuDao.updateFoodItem(
                FoodItemEntity(
                    id = foodItem.id,
                    name = foodItem.name,
                    price = foodItem.price,
                    imageResource = foodItem.imageResource,
                    quantity = foodItem.quantity
                )
            )

        }
    }
    override suspend fun clearCart() {
        withContext(Dispatchers.IO) {
            menuDao.clearCart()
        }
    }
    override suspend fun updateFoodItemQuantity(itemId: Int, newQuantity: Int) {
        withContext(Dispatchers.IO) {
            menuDao.updateFoodItemQuantity(itemId, newQuantity)
        }
    }
    override suspend fun getFoodItemByName(name: String): FoodItem {
        return withContext(Dispatchers.IO) {
            menuDao.getFoodItemByName(name).let {
                FoodItem(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    imageResource = it.imageResource,
                    quantity = it.quantity
                )
            }
        }
    }
}