package com.dantesys.portifolio.presentation.di

import com.dantesys.portifolio.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModel {
    fun load(){
        loadKoinModules(presentationModel())
    }
    private fun presentationModel(): Module {
        return module {
            viewModel {
                MainViewModel(get())
            }
        }
    }
}