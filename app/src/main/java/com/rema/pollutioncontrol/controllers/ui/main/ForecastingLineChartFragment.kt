package com.rema.pollutioncontrol.controllers.ui.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.utils.MPPointF
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.models.Forecast
import org.joda.time.format.DateTimeFormat
import java.util.ArrayList


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "forecasts"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ForecastingLineChartFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ForecastingLineChartFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ForecastingLineChartFragment : Fragment(), OnChartGestureListener {


    private var weatherForecast = ArrayList<Forecast>()
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var dayTextView: TextView
    private lateinit var chart: LineChart
    private lateinit var dateTextView: TextView
    private lateinit var timeTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            weatherForecast = it.getSerializable(ARG_PARAM1) as ArrayList<Forecast>
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_forecasting_line_chart, container, false)
        dayTextView = root.findViewById(R.id.day)
        dateTextView = root.findViewById(R.id.date)
        timeTextView = root.findViewById(R.id.time)
        chart = root.findViewById(R.id.chart)
        this.setupChart(chart)
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        chart.onChartGestureListener = null
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(entry: Entry)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ForecastingLineChartFragment.
         */
        @JvmStatic
        fun newInstance(forecasts: ArrayList<Forecast>) =
                ForecastingLineChartFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_PARAM1, forecasts)
                    }
                }
    }


    private fun setupChart(chart: LineChart) {

        // no description text
        chart.description.isEnabled = false

        // chart.setDrawHorizontalGrid(false);
        //
        // enable / disable grid background
        chart.setDrawGridBackground(false)
        //        chart.getRenderer().getGridPaint().setGridColor(Color.WHITE & 0x70FFFFFF);

        // enable touch gestures
        chart.setTouchEnabled(true)

        chart.dragDecelerationFrictionCoef = 0.9f


        // enable scaling and dragging
        chart.isDragEnabled = true
        chart.setScaleEnabled(false)

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false)
        chart.isHighlightPerDragEnabled = true

//        chart.setBackgroundColor(color)

        // set custom chart offsets (automatic offset calculation is hereby disabled)

        // add data
        chart.data = setUpData()

        // get the legend (only possible after setting data)
        val l = chart.legend
        l.isEnabled = false

        val highlight = Highlight(0f, 0f, 0)
        chart.highlightValue(highlight, false)

        chart.axisLeft.isEnabled = false
        chart.axisLeft.spaceTop = 40f
        chart.axisLeft.spaceBottom = 40f
        chart.axisRight.isEnabled = false

        chart.xAxis.isEnabled = false
        chart.setVisibleXRangeMaximum(4.toFloat())
        chart.moveViewToX(4.toFloat())
        chart.invalidate()
        chart.animateY(1500, Easing.EaseInOutQuad)
        chart.onChartGestureListener = this
        chart.centerViewTo(0f, 0f, YAxis.AxisDependency.RIGHT)
        Handler().postDelayed({
            lineChartPositionChanged()
        }, 50)
    }


    private fun setUpData(): LineData {

        val values = ArrayList<Entry>()
        weatherForecast.forEachIndexed { index, e ->
            values.add(Entry(index.toFloat(), e.weather.qualityIndex.index.toFloat(), activity?.getDrawable(e.weather.icon())))
        }
        // create a dataset and give it a type
        val set1 = LineDataSet(values, "DataSet 1")
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        set1.lineWidth = 1.75f
        set1.circleRadius = 5f
        set1.circleHoleRadius = 5f
        set1.color = Color.WHITE
        set1.setCircleColor(Color.WHITE)
        set1.highLightColor = Color.WHITE
        set1.circleHoleColor = Color.RED
        set1.setDrawCircleHole(true)
        set1.setDrawValues(true)
        set1.mode = LineDataSet.Mode.CUBIC_BEZIER
        set1.enableDashedLine(20f, 10f, 0f)
        set1.setDrawValues(false)
        set1.setDrawIcons(true)
        set1.valueTextSize = 60f
        set1.iconsOffset = MPPointF(0f, -25f)
        // set this to false to disable the drawing of highlight indicator (lines)
        set1.setDrawHighlightIndicators(false)
        // create a data object with the data sets
        return LineData(set1)
    }

    private fun lineChartPositionChanged() {
        val entry: Entry? = chart.getEntryByTouchPoint(0f, 0f)
        if (entry != null) {
            val forecast = weatherForecast[entry.x.toInt()]
            dayTextView.text = forecast.date.dayOfWeek().asText
            val formatDate = DateTimeFormat.forPattern("MMM dd yyyy")
            val formatTime = DateTimeFormat.forPattern("h:mm a")
            dateTextView.text = formatDate.print(forecast.date)
            timeTextView.text = formatTime.print(forecast.date)

            listener?.onFragmentInteraction(entry)
        }
    }


    override fun onChartGestureEnd(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
    }

    override fun onChartFling(me1: MotionEvent?, me2: MotionEvent?, velocityX: Float, velocityY: Float) {

    }

    override fun onChartSingleTapped(me: MotionEvent?) {
    }

    override fun onChartGestureStart(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
    }

    override fun onChartScale(me: MotionEvent?, scaleX: Float, scaleY: Float) {
    }

    override fun onChartLongPressed(me: MotionEvent?) {
    }

    override fun onChartDoubleTapped(me: MotionEvent?) {
    }

    override fun onChartTranslate(me: MotionEvent?, dX: Float, dY: Float) {
        lineChartPositionChanged()
    }
}
