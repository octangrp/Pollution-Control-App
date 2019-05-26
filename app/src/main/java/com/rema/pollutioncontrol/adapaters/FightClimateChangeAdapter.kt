package com.rema.pollutioncontrol.adapaters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.rema.pollutioncontrol.R

class FightClimateChangeAdapter(private val arrayList: ArrayList<String>, var context: Context) : RecyclerView.Adapter<FightClimateChangeAdapter.ViewHolder>() {


    class ViewHolder(linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout) {
        var number: TextView = linearLayout.findViewById<View>(R.id.card_number) as TextView
        var body: TextView = linearLayout.findViewById<View>(R.id.card_body) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.fight_climate_change_list, parent, false) as LinearLayout
        return ViewHolder(linearLayout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        // get current activity from the list
        val item = arrayList[position]
        view.number.text = (position + 1).toString()
        view.body.text = item
    }

}