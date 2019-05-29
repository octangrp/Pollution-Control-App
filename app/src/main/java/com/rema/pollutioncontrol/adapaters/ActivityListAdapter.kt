package com.rema.pollutioncontrol.adapaters

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.CardView
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
        var shape: CardView = linearLayout.findViewById(R.id.shape) as CardView
        var card: CardView = linearLayout.findViewById(R.id.card) as CardView
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

//        if ((position + 1) % 2 == 0) {
//            holder.card.setCardBackgroundColor(Color.parseColor("#0275FB"))
//        }
        if (position == 0) {
            ViewTools.setMargins(context, holder.linearLayout, 100, 0, 10, 0)
        }
        if (position == activities.size - 1) {
            ViewTools.setMargins(context, holder.linearLayout, 10, 0, 100, 0)
        }
        holder.shape.setCardBackgroundColor(context.resources.getColor(activity.badgeColor()))
        holder.icon.setImageDrawable(context.getDrawable(activity.icon))
        holder.name.text = context.getText(activity.name)
    }

}