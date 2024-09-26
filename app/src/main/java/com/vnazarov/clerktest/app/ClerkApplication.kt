package com.vnazarov.clerktest.app

import android.app.Application
import com.vnazarov.clerktest.di.dataModule
import com.vnazarov.clerktest.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ClerkApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ClerkApplication)
            modules(listOf(
                dataModule,
                domainModule
            ))
        }
    }
}