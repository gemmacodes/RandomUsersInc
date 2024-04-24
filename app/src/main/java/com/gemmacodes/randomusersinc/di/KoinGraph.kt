package com.gemmacodes.randomusersinc.di

import androidx.room.Room
import com.gemmacodes.randomusersinc.data.UserRepository
import com.gemmacodes.randomusersinc.data.api.RandomUserRetrofit
import com.gemmacodes.randomusersinc.data.room.UserDatabase
import com.gemmacodes.randomusersinc.viewmodel.UserDetailViewModel
import com.gemmacodes.randomusersinc.viewmodel.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinGraph {

    val dataModule = module {
        single {
            Room.databaseBuilder(get(), UserDatabase::class.java, "random_user_database")
                .fallbackToDestructiveMigration()
                .build()
        }
        single { get<UserDatabase>().userDao() }
        single { get<UserDatabase>().deletedUserDao() }
        single<RandomUserRetrofit.RandomUserService> { RandomUserRetrofit.service }
        single<RandomUserRetrofit.RandomUserService> { RandomUserRetrofit.service }
        single<UserRepository> { UserRepository(get(), get(), get()) }
    }

    val viewModelModule = module {
        viewModel { UserListViewModel(get()) }
        viewModel { UserDetailViewModel(get(), get()) }
    }
}

