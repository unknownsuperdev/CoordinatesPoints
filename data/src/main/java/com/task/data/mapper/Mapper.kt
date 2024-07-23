package com.task.data.mapper

interface Mapper<RESPONSE, UI> {
    fun transform(data: RESPONSE): UI
}
