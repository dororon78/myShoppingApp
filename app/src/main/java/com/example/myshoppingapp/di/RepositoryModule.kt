package com.example.myshoppingapp.di

import com.example.myshoppingapp.data.externalapp.ExternalAppRepositoryImpl
import com.example.myshoppingapp.data.shoppingitem.ShoppingListRepositoryImpl
import com.example.myshoppingapp.domain.repository.ExternalAppRepository
import com.example.myshoppingapp.domain.repository.ShoppingListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindShoppingListRepository(shoppingListRepositoryImpl: ShoppingListRepositoryImpl): ShoppingListRepository

    @Binds
    @Singleton
    abstract fun bindExternalAppRepository(externalAppRepositoryImpl: ExternalAppRepositoryImpl): ExternalAppRepository
}