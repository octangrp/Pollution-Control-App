package com.rema.pollutioncontrol.controllers.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.adapaters.ActivityListAdapter
import com.rema.pollutioncontrol.controllers.ActivityViewActivity
import com.rema.pollutioncontrol.controllers.ViewCityActivity
import com.rema.pollutioncontrol.models.Activity
import com.rema.pollutioncontrol.models.Location
import com.rema.pollutioncontrol.models.Weather
import com.rema.pollutioncontrol.repository.prefs.LanguagePrefs

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : androidx.fragment.app.Fragment(), ActivityListAdapter.ItemClickListener {

    private lateinit var pageViewModel: PageViewModel
    private lateinit var location: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
            location = arguments?.getSerializable(ARG_LOCATION) as Location
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_tabbed, container, false)
        val mainView = root.findViewById<LinearLayout>(R.id.main_view)
        val intent = Intent(activity, ViewCityActivity::class.java)
        intent.putExtra("location", this.location)
        mainView.setOnClickListener { startActivity(intent) }

        val activityRecyclerView: RecyclerView = root.findViewById(R.id.activity_recycler_view)
        val linearLayout = LinearLayoutManager(activity)
        linearLayout.orientation = RecyclerView.HORIZONTAL
        activityRecyclerView.layoutManager = linearLayout
        val adapter = activity?.let { ActivityListAdapter(location.activities, it, this) }
        activityRecyclerView.adapter = adapter
        root.findViewById<View>(R.id.weather_icon).visibility = View.VISIBLE
        root.findViewById<View>(R.id.condition).visibility = View.VISIBLE
        location.weather?.let { displayData(root, it) }

        return root
    }

    private fun displayData(root: View, weather: Weather) {
        root.findViewById<ImageView>(R.id.weather_icon).setImageDrawable(activity?.getDrawable(weather.qualityIndex.icon()))
        root.findViewById<TextView>(R.id.condition).text = activity!!.getString(weather.qualityIndex.conditionStringId())
        root.findViewById<TextView>(R.id.aqi_index).text = weather.qualityIndex.toString()
        root.findViewById<TextView>(R.id.humidity).text = weather.humidityString()
        root.findViewById<TextView>(R.id.temperature).text = activity?.let { weather.temperatureString(it) }
        root.findViewById<TextView>(R.id.wind_speed).text = activity?.let { weather.windSpeedString(it) }
        root.findViewById<ImageView>(R.id.temperature_icon).setImageDrawable(activity?.getDrawable(weather.icon()))
    }


    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_LOCATION = "location"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int, location: Location): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putSerializable(ARG_LOCATION, location)
                }
            }
        }
    }

    override fun onItemClicked(activ: Activity) {
        val intent = Intent(activity?.applicationContext, ActivityViewActivity::class.java)
        intent.putExtra("activity", activ)
        startActivity(intent)
    }
}