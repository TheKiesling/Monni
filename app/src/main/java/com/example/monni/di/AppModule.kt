package com.example.monni.di

import com.example.monni.data.remote.api.AuthApi
import com.example.monni.data.remote.api.CategoryApi
import com.example.monni.data.remote.firestore.FirestoreAuthApiImpl
import com.example.monni.data.remote.firestore.FirestoreCategoryApiImpl
import com.example.monni.data.repository.auth.AuthRepository
import com.example.monni.data.repository.auth.AuthRepositoryImpl
import com.example.monni.data.repository.categories.CategoryRepository
import com.example.monni.data.repository.categories.CategoryRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthProvider() : FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideAuthApi(auth: FirebaseAuth) : AuthApi = FirestoreAuthApiImpl()

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi) : AuthRepository = AuthRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideApiProvider(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideCategoryApi(db: FirebaseFirestore): CategoryApi = FirestoreCategoryApiImpl(db)

    @Provides
    @Singleton
    fun provideCategoryRepository(api: CategoryApi) : CategoryRepository = CategoryRepositoryImpl(api)
}