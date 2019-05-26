package com.rema.pollutioncontrol.controllers

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.utils.MPPointF
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.models.Location
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.utils.ColorTemplate
import javax.xml.datatype.DatatypeConstants.HOURS
import com.github.mikephil.charting.components.AxisBase
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class ViewCityActivity : AppCompatActivity(), OnChartGestureListener {
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
        setPAQIindex()
    }

    private fun setPAQIindex() {
        val entry: Entry? = chart.getEntryByTouchPoint(0f, 0f)
        if (entry != null) {
            (findViewById<TextView>(R.id.paqi)).text = entry.y.toInt().toString()
        }
    }

    lateinit var chart: LineChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_city)
        (findViewById<View>(R.id.back_button)).setOnClickListener { finish() }
        (findViewById<View>(R.id.weather_icon)).visibility = View.GONE
        val location = intent.getSerializableExtra("location") as Location
        displayWeather(location)
        chart = findViewById(R.id.chart)
        val max = 100
        val data = getData(10, max.toFloat())
//        // add some transparency to the color with "& 0x90FFFFFF"
        setupChart(chart, data, Color.parseColor("#00252E34"))
    }

    private fun displayWeather(location: Location) {
        val weather = location.weather
        hideUnusedFields()
        (findViewById<ImageView>(R.id.weather_gauge)).setImageDrawable(getDrawable(weather.gauge()))
        (findViewById<TextView>(R.id.city_name)).text = location.name
        (findViewById<TextView>(R.id.city_condition)).text = weather.qualityIndex.condition()
        (findViewById<TextView>(R.id.aqi_index)).text = weather.qualityIndex.toString()
        (findViewById<TextView>(R.id.humidity)).text = weather.humidityString()
        (findViewById<TextView>(R.id.temperature)).text = weather.temperatureString()
        (findViewById<TextView>(R.id.wind_speed)).text = weather.windSpeedString()
    }

    private fun hideUnusedFields() {
        (findViewById<View>(R.id.weather_icon)).visibility = View.GONE
        (findViewById<View>(R.id.condition) as TextView).visibility = View.GONE
    }


    private val colors = intArrayOf(Color.rgb(137, 230, 81), Color.rgb(240, 240, 30), Color.rgb(89, 199, 250), Color.rgb(250, 104, 104))

    private fun setupChart(chart: LineChart, data: LineData, color: Int) {

        (data.getDataSetByIndex(0) as LineDataSet).circleHoleColor = color

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
//        chart.setViewPortOffsets(10f, 0f, 10f, 0f)

        // add data
        chart.data = data

        // get the legend (only possible after setting data)
        val l = chart.legend
        l.isEnabled = false

        chart.axisLeft.isEnabled = false
        chart.axisLeft.spaceTop = 40f
        chart.axisLeft.spaceBottom = 40f
        chart.axisRight.isEnabled = false

        chart.xAxis.isEnabled = false
        chart.setVisibleXRangeMaximum(4.toFloat())
        chart.moveViewToX(4.toFloat())
        chart.invalidate()
        chart.onChartGestureListener = this
        setPAQIindex()



    }

    private fun getData(count: Int, range: Float): LineData {

        val values = ArrayList<Entry>()

        values.add(Entry(1F, 40F, getDrawable(R.drawable.ic_weather_cloudy)))
        values.add(Entry(2F, 180F, getDrawable(R.drawable.ic_weather_rainny)))
        values.add(Entry(3F, 60F, getDrawable(R.drawable.ic_weather_sunny)))
        values.add(Entry(4F, 120F, getDrawable(R.drawable.ic_weather_cloudy)))
        values.add(Entry(5F, 50F, getDrawable(R.drawable.ic_weather_rainny)))
        values.add(Entry(6F, 210F, getDrawable(R.drawable.ic_weather_sunny)))
        values.add(Entry(7F, 170F, getDrawable(R.drawable.ic_weather_cloudy)))
        values.add(Entry(8F, 80F, getDrawable(R.drawable.ic_weather_rainny)))
        values.add(Entry(9F, 90F, getDrawable(R.drawable.ic_weather_sunny)))
        values.add(Entry(10F, 270F, getDrawable(R.drawable.ic_weather_cloudy)))
        values.add(Entry(11F, 40F, getDrawable(R.drawable.ic_weather_sunny)))

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
        set1.setDrawValues(true)
        set1.mode = LineDataSet.Mode.CUBIC_BEZIER
        set1.enableDashedLine(20f, 10f, 0f)
        set1.setDrawValues(false)
        set1.setDrawIcons(true)
        set1.valueTextSize = 60f
        set1.iconsOffset = MPPointF(0f, -25f)
        set1.setDrawHighlightIndicators(false)


        // create a data object with the data sets
        return LineData(set1)
    }


}
