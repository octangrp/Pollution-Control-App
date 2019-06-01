package com.rema.pollutioncontrol.adapaters

import android.content.Context
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
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


class ActivityListAdapter(private val activities: ArrayList<Activity>, var context: Context, val listener: ItemClickListener) : androidx.recyclerview.widget.RecyclerView.Adapter<ActivityListAdapter.ViewHolder>() {


    inner class ViewHolder(var linearLayout: LinearLayout) : androidx.recyclerview.widget.RecyclerView.ViewHolder(linearLayout) {
        var icon: ImageView = linearLayout.findViewById<View>(R.id.icon) as ImageView
        var name: TextView = linearLayout.findViewById<View>(R.id.name) as TextView
        var shape: androidx.cardview.widget.CardView = linearLayout.findViewById(R.id.shape) as androidx.cardview.widget.CardView
        var condition: TextView = linearLayout.findViewById(R.id.condition)

        init {
            linearLayout.setOnClickListener {
                listener.onItemClicked(activities[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
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
        holder.condition.text = context.getString(activity.condition())
    }

    interface ItemClickListener {
        fun onItemClicked(activity: Activity)
    }
}