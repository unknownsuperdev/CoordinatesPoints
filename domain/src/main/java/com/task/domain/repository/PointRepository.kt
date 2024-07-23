package com.task.domain.repository

import com.task.domain.model.UiPoint
import kotlinx.coroutines.flow.Flow

interface PointRepository {
    fun getPoints(count: Int): Flow<List<UiPoint>>
}
