package com.rema.pollutioncontrol.controllers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.adapaters.FightClimateChangeAdapter
import java.util.ArrayList

class FightClimateChangeActivity : AppCompatActivity() {
    var itemList = ArrayList<String>()
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val linearLayout = LinearLayoutManager(this)
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = linearLayout
        (findViewById<View>(R.id.back_button)).setOnClickListener { finish() }
        (findViewById<View>(R.id.activity_title) as TextView).text = getString(R.string.fight_climate_change)
        initializeList()
        val adapter = FightClimateChangeAdapter(itemList,this)
        recyclerView.adapter = adapter
    }

    private fun initializeList() {
        itemList.add("Possibly the most of data \n" +
                "visualizations, a timeline tracks \n" +
                "data over a time period.Possibly\n" +
                " the most self-explanatory.")
        itemList.add("Possibly the most of data \n" +
                "visualizations, a timeline tracks \n" +
                "data over a time period.Possibly\n" +
                " the most self-explanatory.")
        itemList.add("Possibly the most of data \n" +
                "visualizations, a timeline tracks \n" +
                "data over a time period.Possibly\n" +
                " the most self-explanatory.")
    }
}
