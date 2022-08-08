package com.dantesys.portifolio.data.repositories

import com.dantesys.portifolio.data.model.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepositories {
    suspend fun listRepo(user:String): Flow<List<Repo>>
}