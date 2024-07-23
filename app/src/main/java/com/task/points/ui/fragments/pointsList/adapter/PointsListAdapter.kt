package com.task.points.ui.fragments.pointsList.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.task.domain.model.UiPoint
import com.task.points.R
import com.task.points.appbase.adapter.BaseAdapter
import com.task.points.appbase.adapter.BaseViewHolder
import com.task.points.databinding.PointItemBinding

class PointsListAdapter :
    BaseAdapter<PointItemBinding, UiPoint, PointsListAdapter.PointsListViewHolder>() {

    class PointsListViewHolder(private val binding: PointItemBinding) :
        BaseViewHolder<UiPoint, PointItemBinding>(binding) {
        override fun bind(item: UiPoint, context: Context) {
            binding.vX.text = context.getString(R.string.point_x_format, item.x)
            binding.vY.text = context.getString(R.string.point_y_format, item.y)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointsListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PointItemBinding.inflate(inflater, parent, false)

        return PointsListViewHolder(binding)
    }
}
