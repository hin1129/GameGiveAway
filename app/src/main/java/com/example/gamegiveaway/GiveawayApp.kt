package com.example.gamegiveaway

import android.app.Application
import com.example.gamegiveaway.database.GiveawaysDatabase
import com.example.gamegiveaway.di.applicationModule
import com.example.gamegiveaway.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module


//app class
class GiveawayApp: Application() {

    override fun onCreate() {
        super.onCreate()

        //pass context(from gaApp), module
        startKoin{
            androidContext(this@GiveawayApp)
            //add more modules if needed
            modules(listOf(networkModule))
            modules(listOf(applicationModule))
        }
    }
}