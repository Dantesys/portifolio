package com.dantesys.portifolio.data.di

import android.util.Log
import com.dantesys.portifolio.data.repositories.RepoRepositories
import com.dantesys.portifolio.data.repositories.RepoRepositoriesImplements
import com.dantesys.portifolio.data.services.GitHubService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModel {
    private const val TAG = "HTTP"
    fun load(){
        loadKoinModules(networkModules() + repositoryModel())
    }
    private fun networkModules():Module{
        return module {
            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.e(TAG,"NetworkModules:\n$it")
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }
            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }
            single {
                createService<GitHubService>(get(),get())
            }
        }
    }
    private fun repositoryModel():Module{
        return module {
            single<RepoRepositories> {
                RepoRepositoriesImplements(get())
            }
        }
    }
    private inline fun <reified T> createService(client: OkHttpClient,factory: GsonConverterFactory):T{
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(factory)
            .build()
            .create(T::class.java)
    }
}