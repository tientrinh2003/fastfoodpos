package com.example.fastfoodpos.data.di

import android.content.Context
import androidx.room.Room
import com.example.fastfoodpos.data.network.FastFoodApi
import com.example.fastfoodpos.data.repository.FastFoodRepositoryImpl
import com.example.fastfoodpos.data.room.AppDatabase
import com.example.fastfoodpos.data.room.DAO.MenuDao
import com.example.fastfoodpos.data.room.DAO.OrderDao
import com.example.fastfoodpos.domain.repository.FastFoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.fastfood.com/"

    @Provides
    @Singleton
    fun provideFastFoodApi(): FastFoodApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FastFoodApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "fast_food_pos_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideMenuDao(database: AppDatabase): MenuDao = database.menuDao()

    @Provides
    @Singleton
    fun provideOrderDao(database: AppDatabase): OrderDao = database.orderDao()

    @Provides
    @Singleton
    fun provideFastFoodRepository(
        api: FastFoodApi,
        menuDao: MenuDao,
        orderDao: OrderDao
    ): FastFoodRepository = FastFoodRepositoryImpl(api, menuDao, orderDao)
}