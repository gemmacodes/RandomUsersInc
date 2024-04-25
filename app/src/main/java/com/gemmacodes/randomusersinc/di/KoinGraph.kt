package com.gemmacodes.randomusersinc.di

import androidx.room.Room
import com.gemmacodes.randomusersinc.data.LocalDataSource
import com.gemmacodes.randomusersinc.data.LocalUserRepository
import com.gemmacodes.randomusersinc.data.RemoteDataSource
import com.gemmacodes.randomusersinc.data.RemoteUserRepository
import com.gemmacodes.randomusersinc.data.api.RandomUserRetrofit
import com.gemmacodes.randomusersinc.data.room.UserDatabase
import com.gemmacodes.randomusersinc.ui.viewmodels.UserDetailViewModel
import com.gemmacodes.randomusersinc.ui.viewmodels.UserListViewModel
import com.gemmacodes.randomusersinc.usecase.DeleteUser
import com.gemmacodes.randomusersinc.usecase.ListUsers
import com.gemmacodes.randomusersinc.usecase.RequestNewUsers
import com.gemmacodes.randomusersinc.usecase.RequestUserDetail
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
        single<RandomUserRetrofit> { RandomUserRetrofit }
        single<LocalDataSource> { LocalUserRepository(get()) }
        single<RemoteDataSource> { RemoteUserRepository(get()) }
    }

    val domainModule = module {
        single<ListUsers> { ListUsers(get()) }
        single<DeleteUser> { DeleteUser(get()) }
        single<RequestNewUsers> { RequestNewUsers(get(), get()) }
        single<RequestUserDetail> { RequestUserDetail(get()) }
    }

    val viewModelModule = module {
        viewModel { UserListViewModel(get(), get(), get()) }
        viewModel { UserDetailViewModel(get(), get()) }
    }
}

