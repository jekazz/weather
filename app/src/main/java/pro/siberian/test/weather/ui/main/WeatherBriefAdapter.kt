package pro.siberian.test.weather.ui.main

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pro.siberian.test.weather.R
import pro.siberian.test.weather.databinding.ListItemWeatherBriefBinding
import pro.siberian.test.weather.model.CityWeather

class WeatherBriefAdapter(private val context: Context, private val weathers: List<CityWeather>) :
    RecyclerView.Adapter<WeatherBriefAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(item: CityWeather)
    }

    var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            View.inflate(
                context,
                R.layout.list_item_weather_brief,
                null
            )
        )
    }

    override fun getItemCount(): Int {
        return weathers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(weathers[position])
        }

        holder.bind(weathers[position])
    }

    class ViewHolder: RecyclerView.ViewHolder {
        private var ui: ListItemWeatherBriefBinding

        constructor(view: View) : super(view) {
            view.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            ui = DataBindingUtil.bind(view)!!
        }

        fun bind(weather: CityWeather) {
            ui.weather = weather
        }

    }
}
