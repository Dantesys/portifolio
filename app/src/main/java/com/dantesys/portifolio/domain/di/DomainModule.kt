package com.dantesys.portifolio.domain.di

import com.dantesys.portifolio.domain.ListReposUC
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {
    fun load(){
        loadKoinModules(useCaseModule())
    }
    private fun useCaseModule(): Module {
        return module {
            factory {
                ListReposUC(get())
            }
        }
    }
}