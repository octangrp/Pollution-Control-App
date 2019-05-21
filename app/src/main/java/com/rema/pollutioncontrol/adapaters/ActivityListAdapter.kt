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
import com.rema.pollutioncontrol.models.Activity
import com.rema.pollutioncontrol.repository.ViewTools
import java.util.*


class ActivityListAdapter(private val activities: ArrayList<Activity>, var context: Context) : RecyclerView.Adapter<ActivityListAdapter.ViewHolder>() {


    class ViewHolder(var linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout) {
        var icon: ImageView = linearLayout.findViewById<View>(R.id.icon) as ImageView
        var name: TextView = linearLayout.findViewById<View>(R.id.name) as TextView
        var speed: TextView = linearLayout.findViewById<View>(R.id.speed) as TextView
        var temperature: TextView = linearLayout.findViewById<View>(R.id.temperature) as TextView
        var shape: LinearLayout = linearLayout.findViewById(R.id.shape) as LinearLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ActivityListAdapter.ViewHolder {
        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_list_item, parent, false) as LinearLayout

        return ViewHolder(linearLayout)
    }

    override fun getItemCount(): Int {
        return activities.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // get current activity from the list
        val activity = activities[position]

        if ((position + 1) % 2 == 0) {
            holder.linearLayout.setBackgroundResource(R.drawable.card_accent_rounded_xl)
            holder.shape.setBackgroundResource(R.drawable.card_white_rounded_full)
            ViewTools.setHeight(context, holder.linearLayout, 180)
        } else {
            ViewTools.setMargins(context, holder.linearLayout, 10, 10, 10, 0)
        }
        if (position == 0) {
            ViewTools.setMargins(context, holder.linearLayout, 100, 10, 10, 0)
        }
        if (position == activities.size - 1) {
            ViewTools.setMargins(context, holder.linearLayout, 10, 10, 100, 0)
        }

        holder.icon.setImageDrawable(activity.icon)
        holder.name.text = activity.name
        holder.speed.text = activity.speedString()
        holder.temperature.text = activity.temperatureString()
    }

}