package com.example.fastfoodpos.data.room

import androidx.room.TypeConverter
import com.example.fastfoodpos.domain.model.CartItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartItemTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromCartItemList(cartItems: List<CartItem>?): String? {
        return cartItems?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toCartItemList(cartItemsString: String?): List<CartItem>? {
        return cartItemsString?.let {
            val type = object : TypeToken<List<CartItem>>() {}.type
            gson.fromJson(it, type)
        }
    }
}