package com.task.points.extensions
import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Int.correctColor(ctx: Context, @ColorRes color: Int) =
    if (this != -1)
        this
    else
        ContextCompat.getColor(ctx, color)
