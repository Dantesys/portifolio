package com.dantesys.portifolio.core

import kotlinx.coroutines.flow.Flow


abstract class UseCase<Param,Source> {
    abstract suspend fun execute(param: Param): Flow<Source>
    open suspend operator fun invoke(param: Param) = execute(param)
    abstract class NoParam<Source>: UseCase<None, Flow<Source>>() {
        abstract suspend fun execute(): Flow<Source>
        final override suspend fun execute(param:None) = throw UnsupportedOperationException()
        open suspend operator fun invoke() = execute()
    }
    abstract class NoSource<Param>: UseCase<Param, Unit>() {
        override suspend operator fun invoke(param: Param) = execute(param)
    }
    object None
}