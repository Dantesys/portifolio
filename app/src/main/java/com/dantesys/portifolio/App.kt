package com.dantesys.portifolio

import android.app.Application
import com.dantesys.portifolio.data.di.DataModel
import com.dantesys.portifolio.domain.di.DomainModule
import com.dantesys.portifolio.presentation.di.PresentationModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
        }
        DataModel.load()
        DomainModule.load()
        PresentationModel.load()
    }
}