package com.task.domain.usecases

import com.task.domain.dispatcher.CoroutineDispatcherProvider
import com.task.domain.model.UiPoint
import com.task.domain.repository.PointRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Factory

interface GetPointsUseCase {
    operator fun invoke(count: Int): Flow<List<UiPoint>>
}

@Factory
class GetPointsUseCaseImpl(
    private val repository: PointRepository,
    private val dispatcher: CoroutineDispatcherProvider
): GetPointsUseCase {
    override fun invoke(count: Int): Flow<List<UiPoint>> =
        repository.getPoints(count).flowOn(dispatcher.io)
}
