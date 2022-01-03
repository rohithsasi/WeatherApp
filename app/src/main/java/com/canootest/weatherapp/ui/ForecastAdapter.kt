/*
 * RealWear Development Software, Source Code and Object Code.
 * (c) RealWear, Inc. All rights reserved.
 *
 * Contact info@realwear.com for further information about the use of this code.
 */
package com.canootest.weatherapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.canootest.weatherapp.R
import com.canootest.weatherapp.WeatherApp
import com.canootest.weatherapp.model.WeatherSummary
import com.canootest.weatherapp.utils.toIcon
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter(
) : RecyclerView.Adapter<ForecastAdapter.WeatherDataViewHolder>() {

    private var items = mutableListOf<WeatherSummary>()
    val arrDay = arrayOf("Monday", "Tuesday", "Wednesday, Thursday,Friday,Saturday,Sunday")

    fun updateItems(results: List<WeatherSummary>) {
        items = results as MutableList<WeatherSummary>
        items.removeLast()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_weather, parent, false)
        return WeatherDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherDataViewHolder, position: Int) {
        items.getOrNull(position)?.let { it ->
            holder.bind(it, position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class WeatherDataViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        val day: TextView = view.findViewById(R.id.day)
        val temp: TextView = view.findViewById(R.id.temp)
        val high: TextView = view.findViewById(R.id.temp_high)
        val low: TextView = view.findViewById(R.id.temp_low)
        val icon: ImageView = view.findViewById(R.id.weather)

        /**
         * binds the data with ui elements.
         */
        fun bind(item: WeatherSummary, pos: Int) {
            val degrees = 0x00B0.toChar()
            day.text = toDay(pos)
            //change these to string place holder format
            temp.text = "${item.temp}$degrees"
            high.text = "${item.max}$degrees"
            low.text = "${item.min}$degrees"
            icon.setImageResource(item.main.toIcon(WeatherApp.APPLICATION))
        }

        private fun toDay(pos: Int): String {
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val cal = Calendar.getInstance()
            println("Current Date: " + sdf.format(cal.time))
            cal.add(Calendar.DAY_OF_MONTH, pos)
            val newDate = sdf.format(cal.time)
            //Now just convert this to day.
            return newDate
        }
    }
}