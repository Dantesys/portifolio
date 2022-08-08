package com.dantesys.portifolio.domain

import com.dantesys.portifolio.core.UseCase
import com.dantesys.portifolio.data.model.Repo
import com.dantesys.portifolio.data.repositories.RepoRepositories
import kotlinx.coroutines.flow.Flow

class ListReposUC(
    private val repository:RepoRepositories
): UseCase<String, List<Repo>>() {
    override suspend fun execute(param: String): Flow<List<Repo>> {
        return repository.listRepo(param)
    }

}