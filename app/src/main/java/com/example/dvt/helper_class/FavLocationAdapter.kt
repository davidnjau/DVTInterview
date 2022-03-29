package com.example.dvt.helper_class

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dvt.R
import com.example.dvt.activities.FavouriteLocationDetails
import com.example.dvt.roomdatabase.FavouriteLocationInfo
import com.example.dvt.roomdatabase.WeatherForecastInfo

class FavLocationAdapter(
    private var weatherForecastInfoList: List<FavouriteLocationInfo>,
    private val context: Context
) :
    RecyclerView.Adapter<FavLocationAdapter.Pager2ViewHolder>() {

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val tvDay : TextView = itemView.findViewById(R.id.tvDay)
        val tvTemp : TextView = itemView.findViewById(R.id.tvTemp)
        val tvImage : ImageView = itemView.findViewById(R.id.tvImage)
        val tvWeather : TextView = itemView.findViewById(R.id.tvWeather)

        init {
            itemView.setOnClickListener(this)


        }

        override fun onClick(p0: View?) {

            val position = adapterPosition
            val id = weatherForecastInfoList[position].id

            val formatterHelper = FormatterHelper()
            formatterHelper.saveSharedPreference(context, "favLocationId", id.toString())

            val intent = Intent(context, FavouriteLocationDetails::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Pager2ViewHolder {
        return Pager2ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.favourite_locations,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {

        val formatterHelper = FormatterHelper()

        val date = weatherForecastInfoList[position].date
        val name = weatherForecastInfoList[position].name
        val time = weatherForecastInfoList[position].time
        val temp = weatherForecastInfoList[position].temp
        val weather = weatherForecastInfoList[position].weather
        val weatherDesc = weatherForecastInfoList[position].weatherDescription

        when (weather) {
            "Rain" -> {
                holder.tvImage.setBackgroundResource(R.drawable.rain2)
            }
            "Clouds" -> {
                holder.tvImage.setBackgroundResource(R.drawable.partlysunny1)
            }
            "Clear" -> {
                holder.tvImage.setBackgroundResource(R.drawable.clear1)
            }
            "Sunny" -> {
                holder.tvImage.setBackgroundResource(R.drawable.clear2)

            }
            else -> {
                holder.tvImage.setBackgroundResource(R.drawable.clear)
            }
        }
        //Snow, Fog, Mist, Drizzle

        val dayOfWeek = formatterHelper.getDays(date)

        holder.tvDay.text = name
        holder.tvWeather.text = weatherDesc
        holder.tvTemp.text = temp.toString()


    }

    override fun getItemCount(): Int {
        return weatherForecastInfoList.size
    }

}