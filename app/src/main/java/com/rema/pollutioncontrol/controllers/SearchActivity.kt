package com.rema.pollutioncontrol.controllers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.rema.pollutioncontrol.R
import android.text.Editable
import com.rema.pollutioncontrol.adapaters.SearchAdapter
import com.rema.pollutioncontrol.models.Location
import com.rema.pollutioncontrol.repository.prefs.LocationListPrefs


class SearchActivity : AppCompatActivity(), SearchAdapter.ItemClickListener {

    private lateinit var adapter: SearchAdapter
    private var locationList = ArrayList<Location>()
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val linearLayout = LinearLayoutManager(this)
        linearLayout.orientation = RecyclerView.VERTICAL
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = linearLayout
        setUpLocations()
        adapter = SearchAdapter(locationList, this, this)
        recyclerView.adapter = adapter
        (findViewById<View>(R.id.back_button)).setOnClickListener { finish() }
        (findViewById<EditText>(R.id.search_field)).addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                this@SearchActivity.adapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun setUpLocations() {
        locationList.add(Location("Muhanga", "Southern Province"))
        locationList.add(Location("Huye", "Southern Province"))
        locationList.add(Location("Nyanza", "Southern Province"))
        locationList.add(Location("Nyamagabe", "Southern Province"))
        locationList.add(Location("Ruhango", "Southern Province"))
        locationList.add(Location("Karongi", "Western Province"))
        locationList.add(Location("Rubavu", "Western Province"))
        locationList.add(Location("Rusizi", "Western Province"))
        locationList.add(Location("Nyamasheke", "Western Province"))
        locationList.add(Location("Bugesera", "Eastern Province"))
        locationList.add(Location("Kirehe", "Eastern Province"))
//        locationList = LocationListPrefs(this).getList()
    }

    override fun onItemClicked(location: Location) {
        LocationListPrefs(this).saveToList(location)
        finish()
    }

}
