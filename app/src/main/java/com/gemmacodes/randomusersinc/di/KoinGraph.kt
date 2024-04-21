package com.gemmacodes.randomusersinc.di

import androidx.room.Room
import com.gemmacodes.randomusersinc.RandomUserViewModel
import com.gemmacodes.randomusersinc.data.RandomUserRepository
import com.gemmacodes.randomusersinc.data.room.RandomUserDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinGraph {

    val roomModule = module {
        single {
            Room.databaseBuilder(get(), RandomUserDatabase::class.java, "random_user_database")
                .fallbackToDestructiveMigration()
                .build()
        }
        single { get<RandomUserDatabase>().randomUserDao() }
    }

    val repoModule = module {
        single<RandomUserRepository> { RandomUserRepository() }
    }

    val viewModelModule = module {
        viewModel { RandomUserViewModel(get()) }
    }
}

