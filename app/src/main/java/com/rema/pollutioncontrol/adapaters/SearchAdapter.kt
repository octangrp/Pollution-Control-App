package com.rema.pollutioncontrol.adapaters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.models.Location
import java.util.*
import android.text.method.TextKeyListener.clear


class SearchAdapter(private val locations: ArrayList<Location>, var context: Context, val listener: ItemClickListener) : RecyclerView.Adapter<SearchAdapter.ViewHolder>(), Filterable {
    private var results = ArrayList<Location>()

    init {
        results = locations
    }

    inner class ViewHolder(linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout) {
        var title: TextView = linearLayout.findViewById<View>(R.id.place_name) as TextView
        var provinceName: TextView = linearLayout.findViewById<View>(R.id.place_province) as TextView

        init {
            linearLayout.setOnClickListener {
                listener.onItemClicked(results[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.search_list_view, parent, false) as LinearLayout
        return ViewHolder(linearLayout)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        // get current activity from the list
        val location = results[position]
        view.title.text = location.name
        view.provinceName.text = location.province
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                results = if (charString.isEmpty()) {
                    locations
                } else {
                    val filteredList = ArrayList<Location>()
                    for (location in locations) {
                        if (location.name.toLowerCase().contains(charString.toLowerCase()) || location.province.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(location)
                        }
                    }
                    filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = results
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                results = filterResults.values as ArrayList<Location>
                notifyDataSetChanged()
            }
        }
    }

    interface ItemClickListener {
        fun onItemClicked(location: Location)
    }

}