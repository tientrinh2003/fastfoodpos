package com.example.fastfoodpos.di

import android.content.Context
import androidx.room.Room
import com.example.fastfoodpos.data.network.AssetInterceptor
import com.example.fastfoodpos.data.network.FastFoodApi
import com.example.fastfoodpos.data.repository.impl.CartRepositoryImpl
import com.example.fastfoodpos.data.repository.impl.FastFoodRepositoryImpl
import com.example.fastfoodpos.data.room.AppDatabase
import com.example.fastfoodpos.data.room.DAO.CartDAO
import com.example.fastfoodpos.data.room.DAO.MenuDao
import com.example.fastfoodpos.data.room.DAO.OrderDao
import com.example.fastfoodpos.domain.repository.CartRepository
import com.example.fastfoodpos.domain.repository.FastFoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFastFoodApi(@ApplicationContext context: Context): FastFoodApi {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AssetInterceptor(context)) // Add the custom interceptor
            .build()

        return Retrofit.Builder()
            .baseUrl("https://mock-api.com/") // Use a dummy base URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(FastFoodApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return try {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "fast_food_database"
            ).build()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    @Provides
    fun provideMenuDao(appDatabase: AppDatabase): MenuDao {
        return appDatabase.menuDao()
    }

    @Provides
    fun provideCartDao(appDatabase: AppDatabase): CartDAO {
        return appDatabase.cartDao()
    }

    @Provides
    fun provideOrderDao(appDatabase: AppDatabase): OrderDao {
        return appDatabase.orderDao()
    }

    @Provides
    @Singleton
    fun provideFastFoodRepository(
        fastFoodApi: FastFoodApi,
        menuDao: MenuDao,
        orderDao: OrderDao
    ): FastFoodRepository {
        return FastFoodRepositoryImpl(fastFoodApi, menuDao, orderDao)
    }

    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDAO): CartRepository {
        return CartRepositoryImpl(cartDao)
    }
}