package com.rema.pollutioncontrol.controllers

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.adapaters.FightClimateChangeAdapter
import java.util.*

class FightClimateChangeActivity : AppCompatActivity() {
    var itemList = ArrayList<String>()
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val linearLayout = LinearLayoutManager(this)
        linearLayout.orientation = RecyclerView.VERTICAL
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = linearLayout
        (findViewById<View>(R.id.back_button)).setOnClickListener { finish() }
        (findViewById<View>(R.id.activity_title) as TextView).text = getString(R.string.fight_climate_change)
        initializeList()
        val adapter = FightClimateChangeAdapter(itemList, this)
        recyclerView.adapter = adapter
    }

    private fun initializeList() {
        itemList.add(getString(R.string.fight_climate_change_1))
        itemList.add(getString(R.string.fight_climate_change_2))
        itemList.add(getString(R.string.fight_climate_change_3))
    }
}
