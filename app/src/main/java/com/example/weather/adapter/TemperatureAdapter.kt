package com.example.weather.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toIcon
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.Join
import com.example.weather.R
import java.net.URL

class TemperatureAdapter : RecyclerView.Adapter<TempViewHolder>() {

    private val list = ArrayList<Join>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_forecast, parent, false)
        return TempViewHolder(view)
    }

    override fun onBindViewHolder(holder: TempViewHolder, position: Int) {
        val p = list[position]
        holder.temp.text = p.temp.toString()
        holder.time.text = p.date

        holder.icon.text = p.icon
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getAllList(mList : List<Join>){
        list.clear()
        list.addAll(mList)
        notifyDataSetChanged()
    }

}

class TempViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val temp : TextView = view.findViewById(R.id.tem)
    val time : TextView = view.findViewById(R.id.time)
    val icon : TextView = view.findViewById(R.id.icon)
}