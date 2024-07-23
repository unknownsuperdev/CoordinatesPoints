package com.task.entities.responce

import com.google.gson.annotations.SerializedName

data class PointResponse(
    @SerializedName("points") val points: List<Point>
)
