package com.example.fastfoodpos.di

import android.content.Context
import androidx.room.Room
import com.example.fastfoodpos.data.repository.FastFoodRepositoryImpl
import com.example.fastfoodpos.data.repository.UserRepositoryImpl
import com.example.fastfoodpos.data.repository.impl.CartRepositoryImpl
import com.example.fastfoodpos.data.room.AppDatabase
import com.example.fastfoodpos.data.room.DAO.CartDAO
import com.example.fastfoodpos.data.room.DAO.MenuDao
import com.example.fastfoodpos.data.room.DAO.OrderDao
import com.example.fastfoodpos.data.room.DAO.UserDao
import com.example.fastfoodpos.data.room.MIGRATION_1_2
import com.example.fastfoodpos.domain.repository.CartRepository
import com.example.fastfoodpos.domain.repository.FastFoodRepository
import com.example.fastfoodpos.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return try {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "fast_food_database"
            )
                .addMigrations(MIGRATION_1_2)
                .build()
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
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideFastFoodRepository(
        menuDao: MenuDao,
        orderDao: OrderDao
    ): FastFoodRepository {
        return FastFoodRepositoryImpl(menuDao, orderDao)
    }

    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDAO): CartRepository {
        return CartRepositoryImpl(cartDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }
}