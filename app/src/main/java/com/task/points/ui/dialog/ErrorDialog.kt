package com.task.points.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.view.Gravity
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.task.points.appbase.utils.viewBinding
import com.task.points.databinding.DialogErrorBinding

class ErrorDialog(
    private val onDismiss: (() -> Unit)? = null
) : DialogFragment() {

    private val binding: DialogErrorBinding by viewBinding()

    private var message: String? = null

    @SuppressWarnings("MagicNumber")
    private val dialogWithPercent: Float = 0.8f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        message = arguments?.getString(EXTRA_MESSAGE)
    }

    override fun onResume() {
        super.onResume()
        val metrics: DisplayMetrics = this.resources.displayMetrics
        val width = metrics.widthPixels
        dialog?.let {
            it.window?.setLayout(
                (width * dialogWithPercent).toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            it.window?.setGravity(Gravity.CENTER)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setOnDismissListener {
                onDismiss?.invoke()
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            massage.text = message
            ok.setOnClickListener {
                onDismiss
                dismiss()
            }
        }
    }

    companion object {

        const val TAG = "ErrorDialog"

        private const val EXTRA_MESSAGE = "message"

        fun newInstance(message: String? = null, onDismiss: (() -> Unit)? = null): ErrorDialog {
            val dialog = ErrorDialog(onDismiss)
            val args = Bundle().apply {
                message?.let { putString(EXTRA_MESSAGE, it) }
            }
            dialog.arguments = args
            return dialog
        }
    }
}
