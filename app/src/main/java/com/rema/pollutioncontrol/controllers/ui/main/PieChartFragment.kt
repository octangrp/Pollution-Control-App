package com.rema.pollutioncontrol.controllers.ui.main

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.models.AirQualityIndex

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "AirQualityIndex"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PieChartFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PieChartFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PieChartFragment : androidx.fragment.app.Fragment() {
    lateinit var chart: PieChart
    var qualityIndex: AirQualityIndex? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            qualityIndex = it.getSerializable(ARG_PARAM1) as AirQualityIndex
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_pie_chart, container, false)
        chart = root.findViewById(R.id.pie_chart)
        setUpChart()
        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param airQualityIndex AirQualityIndex Object.
         * @return A new instance of fragment PieChartFragment.
         */
        @JvmStatic
        fun newInstance(airQualityIndex: AirQualityIndex) =
                PieChartFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_PARAM1, airQualityIndex)
                    }
                }
    }


    private fun setUpChart() {
        chart.setUsePercentValues(true)
        chart.description.isEnabled = false
        chart.setExtraOffsets(0f, 0f, 0f, 0f)

        chart.dragDecelerationFrictionCoef = 0.95f

        chart.isDrawHoleEnabled = true
        chart.setHoleColor(Color.TRANSPARENT)

        chart.setTransparentCircleColor(Color.TRANSPARENT)
        chart.setTransparentCircleAlpha(110)


        chart.setDrawCenterText(true)

        chart.rotationAngle = 180f
        // enable rotation of the chart by touch
        chart.isRotationEnabled = false
        chart.isHighlightPerTapEnabled = false

        val typeface: Typeface? = activity?.let { ResourcesCompat.getFont(it, R.font.comfortaa) }

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);


       chart.centerText = qualityIndex?.condition() ?: "---"
        chart.setCenterTextTypeface(typeface)
        chart.holeRadius = 67f
        chart.transparentCircleRadius = 67f
        chart.setCenterTextColor(Color.WHITE)
        chart.setCenterTextSize(23f)
        chart.animateY(1500, Easing.EaseInOutQuad)
//         chart.spin(2000, 0f, 180f,Easing.EaseInOutQuad);

        val l = chart.legend
        l.isEnabled = false

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        chart.setDrawEntryLabels(false)
       chart.data = this.setUpChartData()
        chart.maxAngle = 180f
    }


    fun updateChart(airQualityIndex: AirQualityIndex) {
        qualityIndex = airQualityIndex
        chart.centerText = airQualityIndex.condition()
        chart.data = setUpChartData()
        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    private fun setUpChartData(): PieData {
        val yValue = ArrayList<PieEntry>()
        qualityIndex?.percent()?.toFloat()?.let { PieEntry(it) }?.let { yValue.add(it) }
        yValue.add(PieEntry((100 - (qualityIndex?.percent() ?: 0)).toFloat()))

        val dataSet = PieDataSet(yValue, "")
        dataSet.valueTextSize = 0f
        val colors = java.util.ArrayList<Int>()
        qualityIndex?.conditionColor()?.let { colors.add(it) }
        colors.add(Color.parseColor("#EAEAEA"))
        dataSet.colors = colors
        return PieData(dataSet)
    }
}
