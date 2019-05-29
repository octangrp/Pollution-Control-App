package com.rema.pollutioncontrol.controllers

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
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
import com.rema.pollutioncontrol.models.Place
import com.rema.pollutioncontrol.models.Weather
import com.rema.pollutioncontrol.repository.seeders.ForecastingDataSeeder
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.ArrayList

class ViewPlaceActivity : AppCompatActivity(), OnChartGestureListener {

    private lateinit var chart: LineChart
    var weatherForecast = ArrayList<Forecast>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_place)
        val place = intent.getSerializableExtra("place") as Place
        (findViewById<View>(R.id.back_button)).setOnClickListener { finish() }
        (findViewById<ImageView>(R.id.forest_main_pic)).setImageDrawable(getDrawable(place.main_pic))
        (findViewById<TextView>(R.id.forest_name)).text = place.name
        displayWeatherDetails(place.weather)
        weatherForecast.add(Forecast(DateTime(), place.weather))
        (findViewById<TextView>(R.id.main_temperature)).text = place.weather.temperatureString()
        (findViewById<TextView>(R.id.weather_condition)).text = getString(place.weather.conditionStringResource())
        (findViewById<TextView>(R.id.forest_description)).text = place.description
        weatherForecast.addAll(ForecastingDataSeeder.run(this))
        chart = findViewById(R.id.chart)
        setupChart(chart)
    }

    private fun displayWeatherDetails(weather: Weather) {
        (findViewById<TextView>(R.id.temperature)).text = weather.temperatureString()
        (findViewById<TextView>(R.id.humidity)).text = weather.humidityString()
        (findViewById<TextView>(R.id.wind_speed)).text = weather.windSpeedString()
        (findViewById<ImageView>(R.id.temperature_icon)).setImageDrawable(getDrawable(weather.icon()))
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
        chart.data = getData()

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
            setDisplayChartData()
        }, 50)


    }


    private fun getData(): LineData {

        val values = ArrayList<Entry>()
        weatherForecast.forEachIndexed { index, e ->
            values.add(Entry(index.toFloat(), e.weather.qualityIndex.index.toFloat(), getDrawable(e.weather.icon())))
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
        setDisplayChartData()
    }

    private fun setDisplayChartData() {
        val entry: Entry? = chart.getEntryByTouchPoint(0f, 0f)
        if (entry != null) {
            val forecast = weatherForecast[entry.x.toInt()]
//            (findViewById<TextView>(R.id.aqi_index)).text = entry.y.toInt().toString()
            (findViewById<TextView>(R.id.day)).text = forecast.date.dayOfWeek().asText
            val formatDate = DateTimeFormat.forPattern("MMM dd yyyy")
            val formatTime = DateTimeFormat.forPattern("h:mm a")
            (findViewById<TextView>(R.id.date)).text = formatDate.print(forecast.date)
            (findViewById<TextView>(R.id.time)).text = formatTime.print(forecast.date)
            displayWeatherDetails(forecast.weather)
        }
    }
}
