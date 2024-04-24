package com.gemmacodes.randomusersinc

import android.app.Application
import com.gemmacodes.randomusersinc.di.KoinGraph.apiModule
import com.gemmacodes.randomusersinc.di.KoinGraph.roomModule
import com.gemmacodes.randomusersinc.di.KoinGraph.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class RandomUserIncApp : Application() {
    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidContext(this@RandomUserIncApp)
            androidLogger()
            modules(roomModule, apiModule, viewModelModule)
        }
    }
}