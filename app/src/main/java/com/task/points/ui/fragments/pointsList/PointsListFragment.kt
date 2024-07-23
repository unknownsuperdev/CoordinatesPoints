package com.task.points.ui.fragments.pointsList

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.task.points.R
import com.task.points.appbase.FragmentBaseNCMVI
import com.task.points.appbase.utils.viewBinding
import com.task.points.databinding.FragmentPointsListBinding
import com.task.points.ui.fragments.pointsList.adapter.PointsListAdapter
import com.task.points.ui.fragments.pointsList.mvi.PointsListAction
import com.task.points.ui.fragments.pointsList.mvi.PointsListIntent
import com.task.points.ui.fragments.pointsList.mvi.PointsListNavigationCommand
import com.task.points.ui.fragments.pointsList.mvi.PointsListState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val CHART_IMG_NAME_PREFIX = "Chart"

class PointsListFragment : FragmentBaseNCMVI<
        PointsListState,
        PointsListAction,
        PointsListIntent,
        PointsListNavigationCommand,
        PointsListViewModel,
        FragmentPointsListBinding>() {

    override val viewModel: PointsListViewModel by viewModel {
        parametersOf(args.count)
    }
    override val binding: FragmentPointsListBinding by viewBinding()
    private val pointsListAdapter: PointsListAdapter by lazy { PointsListAdapter() }
    private val args: PointsListFragmentArgs by navArgs()

    override fun initView() {
        val manager = LinearLayoutManager(requireContext())
        with(binding) {
            rvData.adapter = pointsListAdapter
            rvData.layoutManager = manager
        }
    }

    override fun renderState(state: PointsListState) {
        pointsListAdapter.submitList(state.points)
        setChartData(state.entries)
    }

    override fun initListeners() {
        with(binding) {
            vBackButton.setOnClickListener {
                viewModel.handleNavigation(PointsListNavigationCommand.Back)
            }
            vScreenshotButton.setOnClickListener {
                saveChartAsImage()
            }
        }
    }

    private fun setChartData(entries: List<Entry>) {
        with(binding) {
            val set: LineDataSet
            if (vChart.data != null &&
                vChart.data.getDataSetCount() > 0
            ) {
                set = vChart.data.getDataSetByIndex(0) as LineDataSet
                set.setValues(entries)
                set.notifyDataSetChanged()
                vChart.data.notifyDataChanged()
                vChart.notifyDataSetChanged()
                vChart.invalidate()
            } else {
                set = LineDataSet(entries, getString(R.string.points_chart_label))
                set.setLineWidth(1f)
                set.circleRadius = 3f
                set.valueTextSize = 9f;
                set.mode = LineDataSet.Mode.CUBIC_BEZIER

                val dataSets = listOf(set)
                val data = LineData(dataSets);
                vChart.setData(data);
                vChart.invalidate()
            }
        }
    }

    private fun saveChartAsImage() {
        val bitmap = binding.vChart.drawToBitmap()
        val filename = "$CHART_IMG_NAME_PREFIX " +
                "${DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now())}.jpg"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val fos: OutputStream?
            val imageUri: Uri?
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                put(MediaStore.Video.Media.IS_PENDING, 1)
            }

            val contentResolver = requireContext().applicationContext.contentResolver

            contentResolver.also { resolver ->
                imageUri =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }

            fos?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }

            contentValues.clear()
            contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
            imageUri?.let {
                contentResolver.update(imageUri, contentValues, null, null)
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            val fos = FileOutputStream(image)
            fos.use {bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)}
        }
        Toast.makeText(
            requireContext(),
            R.string.points_chart_screenshot_taken_toast,
            Toast.LENGTH_SHORT
        ).show()
    }
}
