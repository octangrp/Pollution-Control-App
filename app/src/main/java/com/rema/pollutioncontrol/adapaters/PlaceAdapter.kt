package com.rema.pollutioncontrol.adapaters

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.controllers.ViewPlaceActivity
import com.rema.pollutioncontrol.models.Place
import java.util.*

class PlaceAdapter(private val places: ArrayList<Place>, var context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {


    class ViewHolder(var linearLayout: LinearLayout) : androidx.recyclerview.widget.RecyclerView.ViewHolder(linearLayout) {
        var thumbnail: ImageView = linearLayout.findViewById<View>(R.id.card_image) as ImageView
        var temperatureIcon: ImageView = linearLayout.findViewById<View>(R.id.temperature_icon) as ImageView
        var title: TextView = linearLayout.findViewById<View>(R.id.card_title) as TextView
        var temperature: TextView = linearLayout.findViewById<View>(R.id.temperature) as TextView
        var windSpeed: TextView = linearLayout.findViewById<View>(R.id.wind_speed) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.place_list_item, parent, false) as LinearLayout
        return ViewHolder(linearLayout)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        // get current activity from the list
        val place = places[position]
        val intent = Intent(context,ViewPlaceActivity::class.java)
        intent.putExtra("place", place)
        view.linearLayout.setOnClickListener { context.startActivity(intent) }
        view.thumbnail.setImageDrawable(context.getDrawable(place.thumbnail))
        view.temperatureIcon.setImageDrawable(context.getDrawable(place.weather.icon()))
        view.title.text = place.name
        view.temperature.text = place.weather.temperatureString(context)
        view.windSpeed.text = place.weather.windSpeedString(context)
    }
}