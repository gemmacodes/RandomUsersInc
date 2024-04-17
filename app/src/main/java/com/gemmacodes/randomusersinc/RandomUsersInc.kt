package com.gemmacodes.randomusersinc

import android.app.Application
import com.gemmacodes.randomusersinc.di.KoinGraph
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class RandomUsersInc: Application() {
    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidContext(this@RandomUsersInc)
            modules(KoinGraph.mainModule)
        }
    }
}