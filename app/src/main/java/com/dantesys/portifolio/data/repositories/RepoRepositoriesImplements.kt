package com.dantesys.portifolio.data.repositories

import com.dantesys.portifolio.R
import com.dantesys.portifolio.core.RemoteException
import com.dantesys.portifolio.data.model.Repo
import com.dantesys.portifolio.data.services.GitHubService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class RepoRepositoriesImplements(private val service: GitHubService) : RepoRepositories {
    override suspend fun listRepo(user: String): Flow<List<Repo>> = flow {
        try {
            val repoList = service.listRepos(user)
            emit(repoList)
        }catch(e:HttpException) {
            throw RemoteException(e.message?: R.string.erro.toString())
        }
    }
}