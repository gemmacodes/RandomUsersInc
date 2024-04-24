package com.gemmacodes.randomusersinc.di

import androidx.room.Room
import com.gemmacodes.randomusersinc.data.api.ApiHelper
import com.gemmacodes.randomusersinc.data.api.ApiHelperImpl
import com.gemmacodes.randomusersinc.data.api.RandomUserRetrofit
import com.gemmacodes.randomusersinc.data.room.DatabaseHelper
import com.gemmacodes.randomusersinc.data.room.DatabaseHelperImpl
import com.gemmacodes.randomusersinc.data.room.UserDatabase
import com.gemmacodes.randomusersinc.viewmodel.UserDetailViewModel
import com.gemmacodes.randomusersinc.viewmodel.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinGraph {

    val roomModule = module {
        single {
            Room.databaseBuilder(get(), UserDatabase::class.java, "random_user_database")
                .fallbackToDestructiveMigration()
                .build()
        }
        single { get<UserDatabase>().randomUserDao() }
        single<DatabaseHelper> { DatabaseHelperImpl(get()) }
    }

    val apiModule = module {
        single<ApiHelper> { ApiHelperImpl(get()) }
        single<RandomUserRetrofit.RandomUserService> { RandomUserRetrofit.service }
    }

    val viewModelModule = module {
        viewModel { UserListViewModel(get(), get()) }
        viewModel { UserDetailViewModel(get(), get()) }
    }
}

