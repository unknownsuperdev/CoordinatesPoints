package com.task.points.appbase

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.task.points.R
import com.task.points.appbase.mvi.Intent
import com.task.points.appbase.mvi.NavigationCommand
import com.task.points.appbase.mvi.Action
import com.task.points.appbase.mvi.State
import com.task.points.appbase.navigation.NavEvent
import com.task.points.appbase.viewmodel.MviBaseViewModel
import com.task.points.ui.dialog.ErrorDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class FragmentBaseMVI<
        S : State,
        A : Action,
        I : Intent,
        NC : NavigationCommand,
        VM : MviBaseViewModel<S, A, I, NC>,
        ViewBind : ViewBinding> : Fragment() {

    abstract val viewModel: VM
    abstract val binding: ViewBind

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUiState.onEach {
            it.apiException?.let {
                ErrorDialog.newInstance(it.errorInfo.message.orEmpty()) {
                    viewModel.handleNavigate(NavEvent.Back)
                }.show(childFragmentManager, ErrorDialog.TAG)
            }
            renderState(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        initView()
        initListeners()
    }

    fun showLoading(shouldShow: Boolean) {
        val rootView = binding.root as ViewGroup
        var loadingView: FrameLayout? = rootView.findViewById(R.id.loadingView)
        if (loadingView == null) {
            val inflater = LayoutInflater.from(binding.root.context)
            loadingView = inflater.inflate(R.layout.layout_loading, rootView, false) as FrameLayout
            rootView.addView(loadingView)
        }

        loadingView.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }

    protected abstract fun initView()

    protected abstract fun renderState(state: S)

    protected open fun initListeners() {}
}
