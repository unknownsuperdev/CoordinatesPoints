package com.task.data.repository

import com.task.data.dataservice.apiservice.PointService
import com.task.data.mapper.PointResponseToUiMapper
import com.task.data.util.emitFlow
import com.task.domain.model.UiPoint
import com.task.domain.repository.PointRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class PointRepositoryImpl(
    private val pointService: PointService,
    private val pointMapper: PointResponseToUiMapper
) : PointRepository {

    override fun getPoints(count: Int): Flow<List<UiPoint>> = emitFlow {
        pointMapper.transform(pointService.getPoints(count))
    }
}
