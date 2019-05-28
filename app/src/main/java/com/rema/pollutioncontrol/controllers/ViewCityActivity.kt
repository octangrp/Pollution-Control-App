package com.rema.pollutioncontrol.controllers

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
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
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.highlight.Highlight
import com.rema.pollutioncontrol.models.AirQualityIndex
import com.rema.pollutioncontrol.models.Forecast
import com.rema.pollutioncontrol.models.Weather
import com.rema.pollutioncontrol.repository.ForecastingDataSeeder
import com.rema.pollutioncontrol.repository.ViewTools
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask


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
            val oldValue = airQualityIndex
            val forecast = weatherForecast[entry.x.toInt()]
            updateQualityIndex(forecast.weather.qualityIndex.index)
            (findViewById<TextView>(R.id.aqi_index)).text = entry.y.toInt().toString()
            (findViewById<TextView>(R.id.day)).text = forecast.date.dayOfWeek().asText
            val formatDate = DateTimeFormat.forPattern("MMM dd yyyy")
            val formatTime = DateTimeFormat.forPattern("h:mm a")
            (findViewById<TextView>(R.id.date)).text = formatDate.print(forecast.date)
            (findViewById<TextView>(R.id.time)).text = formatTime.print(forecast.date)
            displayWeatherDetails(forecast.weather)
            updatePieChart(oldValue)
        }
    }


    fun updateQualityIndex(index: Int) {
        val qualityIndex = AirQualityIndex(index)
        airQualityIndex = qualityIndex.percent()
        pollutionCondition = qualityIndex.condition()
        pollutionConditionColor = qualityIndex.conditionColor()
    }

    fun updatePieChart(oldValue: Int) {
        if (enableAnimation) {
            enableAnimation = false
            pieChart.animateY(600, Easing.EaseOutBack)
        }
        pieChart.centerText = pollutionCondition
        pieChart.data = getPieChartData()
        pieChart.notifyDataSetChanged()
//        Timer().schedule(timerTask {
//            enableAnimation = true
//        }, 2000)
//        pieChart.refreshDrawableState()
        pieChart.invalidate()

//        pieChart.spin(500, (oldValue * 18).toFloat(), (airQualityIndex * 18).toFloat(), Easing.EaseInSine)

    }

    private lateinit var chart: LineChart
    lateinit var pieChart: PieChart
    lateinit var location: Location
    var airQualityIndex: Int = 0
    var pollutionCondition: String = ""
    var pollutionConditionColor: Int = 0
    var enableAnimation: Boolean = true
    var weatherForecast = ArrayList<Forecast>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_city)
        (findViewById<View>(R.id.back_button)).setOnClickListener { finish() }
        (findViewById<View>(R.id.weather_icon)).visibility = View.GONE
        location = intent.getSerializableExtra("location") as Location
        displayWeather(location)
        setWeatherForecasting()
        chart = findViewById(R.id.chart)
        pieChart = findViewById(R.id.pie_chart)
        val max = 100
        val data = getData(10, max.toFloat())
        updateQualityIndex(location.weather.qualityIndex.index)
//        // add some transparency to the color with "& 0x90FFFFFF"
        setupChart(chart, data, Color.parseColor("#00252E34"))
        setupPieChart(pieChart)
    }

    private fun displayWeather(location: Location) {
        val weather = location.weather
        hideUnusedFields()
        (findViewById<TextView>(R.id.activity_title)).text = location.name
        displayWeatherDetails(weather)
    }

    private fun displayWeatherDetails(weather: Weather) {
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

    private fun setupPieChart(chart: PieChart) {
        chart.setUsePercentValues(true)
        chart.description.isEnabled = false
        chart.setExtraOffsets(0f, 0f, 0f, 0f)

        chart.dragDecelerationFrictionCoef = 0.95f

//        chart.setCenterTextTypeface(tfLight);

        chart.isDrawHoleEnabled = true
        chart.setHoleColor(Color.TRANSPARENT)

        chart.setTransparentCircleColor(Color.TRANSPARENT)
        chart.setTransparentCircleAlpha(110)


        chart.setDrawCenterText(true)

        chart.rotationAngle = 180f
        // enable rotation of the chart by touch
        chart.isRotationEnabled = false
        chart.isHighlightPerTapEnabled = false

        val typeface: Typeface? = ResourcesCompat.getFont(this, R.font.comfortaa)

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
//        chart.setOnChartValueSelectedListener(this);
//
//        seekBarX.setProgress(4);
//        seekBarY.setProgress(10);
        chart.centerText = pollutionCondition
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
        chart.data = this.getPieChartData()
        chart.maxAngle = 180f
    }


    private fun getPieChartData(): PieData {
        val yVals = ArrayList<PieEntry>()
        yVals.add(PieEntry(airQualityIndex.toFloat()))
        yVals.add(PieEntry((100 - airQualityIndex).toFloat()))

        val dataSet = PieDataSet(yVals, "")
        dataSet.valueTextSize = 0f
        val colors = java.util.ArrayList<Int>()
        colors.add(pollutionConditionColor)
        colors.add(Color.parseColor("#EAEAEA"))
        dataSet.colors = colors
        return PieData(dataSet)
    }

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

        // add data
        chart.data = data

        // get the legend (only possible after setting data)
        val l = chart.legend
        l.isEnabled = false

        val highlit = Highlight(0f, 0f, 0)
        chart.highlightValue(highlit, false)

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
        setPAQIindex()


    }

    private fun getData(count: Int, range: Float): LineData {

        val values = ArrayList<Entry>()
        weatherForecast.forEachIndexed { index, e ->
            values.add(Entry(index.toFloat(), e.weather.qualityIndex.index.toFloat(), getDrawable(e.weather.icon(this))))
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


    fun setWeatherForecasting() {
        weatherForecast = ForecastingDataSeeder.run(this)
    }

}
