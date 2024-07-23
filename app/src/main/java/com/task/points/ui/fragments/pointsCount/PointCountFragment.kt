package com.task.points.ui.fragments.pointsCount

import androidx.core.widget.doOnTextChanged
import com.task.points.appbase.FragmentBaseNCMVI
import com.task.points.appbase.utils.viewBinding
import com.task.points.databinding.FragmentPointCountBinding
import com.task.points.ui.fragments.pointsCount.mvi.PointCountAction
import com.task.points.ui.fragments.pointsCount.mvi.PointCountIntent
import com.task.points.ui.fragments.pointsCount.mvi.PointCountNavigationCommand
import com.task.points.ui.fragments.pointsCount.mvi.PointCountState
import org.koin.androidx.viewmodel.ext.android.viewModel

class PointCountFragment : FragmentBaseNCMVI<
        PointCountState,
        PointCountAction,
        PointCountIntent,
        PointCountNavigationCommand,
        PointCountViewModel,
        FragmentPointCountBinding>() {

    override val viewModel: PointCountViewModel by viewModel()
    override val binding: FragmentPointCountBinding by viewBinding()

    override fun initView() {
        binding.vPointCountText.doOnTextChanged { text, _, _, _ ->
            viewModel.onIntent(PointCountIntent.OnCountChanged(text.toString()))
        }
    }

    override fun renderState(state: PointCountState) {
        with(binding) {
            with(state.countState) {
                vPointCount.error = countError?.let{ getString(it) }
                vGo.isEnabled = countError == null && count != 0
            }
        }
    }

    override fun initListeners() {
        binding.vGo.setOnClickListener {
            viewModel.onIntent(PointCountIntent.OpenPointsList)
        }
    }
}