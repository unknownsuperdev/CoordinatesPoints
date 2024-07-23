package com.task.data.mapper

import com.task.domain.model.UiPoint
import com.task.domain.utils.orDefault
import com.task.entities.responce.PointResponse
import org.koin.core.annotation.Single

@Single
class PointResponseToUiMapper : Mapper<PointResponse, List<UiPoint>> {
    override fun transform(data: PointResponse): List<UiPoint> =
        data.points.map { UiPoint(x = it.x.orDefault(), y = it.y.orDefault()) }
}
