package com.task.entities.responce

import com.google.gson.annotations.SerializedName

data class Point(
    @SerializedName("x") val x: Double?,
    @SerializedName("y") val y: Double?
)