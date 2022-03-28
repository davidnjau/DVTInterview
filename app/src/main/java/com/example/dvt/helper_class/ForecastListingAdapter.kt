package com.example.dvt.helper_class

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dvt.R
import com.example.dvt.roomdatabase.WeatherForecastInfo

class ForecastListingAdapter(
    private var weatherForecastInfoList: List<WeatherForecastInfo>,
    private val context: Context
) :
    RecyclerView.Adapter<ForecastListingAdapter.Pager2ViewHolder>() {

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val tvDay : TextView = itemView.findViewById(R.id.tvDay)
        val tvTemp : TextView = itemView.findViewById(R.id.tvTemp)

        init {



        }

        override fun onClick(p0: View?) {

        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Pager2ViewHolder {
        return Pager2ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.forecast_data,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {

        val formatterHelper = FormatterHelper()

        val date = weatherForecastInfoList[position].date
        val time = weatherForecastInfoList[position].time
        val temp = weatherForecastInfoList[position].temp

        //Get Days

        val dayOfWeek = formatterHelper.getDays(date)
        Log.e("----- " , dayOfWeek)

        holder.tvDay.text = "$dayOfWeek $time"
        holder.tvTemp.text = temp.toString()


    }

    override fun getItemCount(): Int {
        return weatherForecastInfoList.size
    }

}