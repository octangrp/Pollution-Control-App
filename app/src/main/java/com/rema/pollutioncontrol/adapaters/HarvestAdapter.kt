package com.rema.pollutioncontrol.adapaters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.models.Harvest
import java.util.*

class HarvestAdapter(private val harvests: ArrayList<Harvest>, var context: Context) : RecyclerView.Adapter<HarvestAdapter.ViewHolder>() {


    class ViewHolder(var linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout) {
        var title: TextView = linearLayout.findViewById<View>(R.id.card_title) as TextView
        var locationsList: TextView = linearLayout.findViewById<View>(R.id.location) as TextView
        var icon: ImageView = linearLayout.findViewById(R.id.plant_icon)
        var temperature: TextView = linearLayout.findViewById(R.id.temperature)
        var temperatureIcon: ImageView = linearLayout.findViewById(R.id.temperature_icon)
        var humidity: TextView = linearLayout.findViewById(R.id.humidity)
        var descriptionWrapper: LinearLayout = linearLayout.findViewById(R.id.description_wrapper)
        var description: TextView = linearLayout.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.harvest_list_item, parent, false) as LinearLayout
        return ViewHolder(linearLayout)
    }

    override fun getItemCount(): Int {
        return harvests.size
    }

    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        // get current activity from the list
        val harvest = harvests[position]
        view.title.text = harvest.name
        view.icon.setImageDrawable(context.getDrawable(harvest.icon))
        view.temperature.text = harvest.weather.temperatureString()
        view.temperatureIcon.setImageDrawable(context.getDrawable(harvest.weather.icon()))
        view.humidity.text = harvest.weather.humidityString()
        var locationString = ""
        for ((index, location) in harvest.locations.withIndex()) {
            locationString += " " + location.name
            if (index < harvest.locations.size - 1) {
                locationString += " |"
            }
        }
        view.locationsList.text = locationString
        view.description.text = harvest.description
        view.linearLayout.setOnClickListener {
            if (view.descriptionWrapper.visibility == View.GONE) {
                view.descriptionWrapper.visibility = View.VISIBLE
            } else {
                view.descriptionWrapper.visibility = View.GONE
            }
        }
    }
}