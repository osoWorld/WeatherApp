package com.example.atmosvista

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.atmosvista.API.WeatherAppData
import com.example.atmosvista.categoriesActivities.CategoriesDetailedActivity
import com.example.atmosvista.databinding.ActivityMainBinding
import com.example.atmosvista.interfaceApi.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cityName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val currentLatitude = intent.getStringExtra("latitude")
        val currentLongitude = intent.getStringExtra("longitude")

//        Log.d("Latitude & Longitude","Latitude: $currentLatitude  Longitude: $currentLongitude")


        locationFromCoordinates(currentLatitude!!.toDouble(), currentLongitude!!.toDouble())

        binding.location.setOnClickListener {
            locationFromCoordinates(currentLatitude!!.toDouble(), currentLongitude!!.toDouble())
            fetchWeatherData(cityName)
        }

//        cityName = "Lahore"

        if (cityName == null) {
            cityName = "Lahore"
        } else {
            fetchWeatherData(cityName)
        }

        fetchWeatherData(cityName)
        searchCity()
    }

    private fun locationFromCoordinates(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: MutableList<android.location.Address>? =
                geocoder.getFromLocation(latitude, longitude, 1)

            if (addresses != null) {
                cityName = addresses[0].locality
                binding.location.text = cityName
            }
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun searchCity() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchWeatherData(query)
                } else {
                    Toast.makeText(this@MainActivity, "City not Found", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        }
        )
    }

    private fun fetchWeatherData(cityName: String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()
            .create(ApiInterface::class.java)

        val response =
            retrofit.getWeatherData(cityName, "27cb987f6873979b4c11aba691c4ed9b", "metric")
        response.enqueue(object : Callback<WeatherAppData> {
            override fun onResponse(
                call: Call<WeatherAppData>,
                response: Response<WeatherAppData>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    val temperature = responseBody!!.main.temp.toString()
                    val humidity = responseBody.main.humidity.toString()
                    val windSpeed = responseBody.wind.speed.toString()
                    val condition = responseBody.weather.firstOrNull()?.main ?: "unknown"
                    val sunRise = responseBody.sys.sunrise.toLong()
                    val sunSet = responseBody.sys.sunset.toLong()
                    val seaLevel = responseBody.main.sea_level.toString()
                    val maxTemp = responseBody.main.temp_max.toString()
                    val minTemp = responseBody.main.temp_min.toString()

                    binding.temperature.text = "$temperature ℃"  //Not included
                    binding.humidityMeasure.text = "$humidity %"
                    binding.windSpeedMeasure.text = "$windSpeed m/s"
                    binding.condition.text = "$condition"  //Not included
                    binding.sunriseMeasure.text = "${time(sunRise)}"
                    binding.sunsetMeasure.text = "${time(sunSet)}"
                    binding.seaLevelMeasure.text = "$seaLevel hPa"
                    binding.maxTemp.text = "Max Temp: $maxTemp ℃"  //Not included
                    binding.minTemp.text = "Min Temp: $minTemp ℃"  //Not included
                    binding.location.text = "$cityName"  //Not included
                    binding.rainMeasure.text = "$condition"

                    cardClicks(
                        humidity = humidity,
                        windSpeed = windSpeed,
                        sunRise = time(sunRise),
                        sunSet = time(sunSet),
                        rainMeter = condition,
                        seaLevel = seaLevel
                    )

                    binding.day.text = dayName(System.currentTimeMillis())
                    binding.date.text = date()

                    changeBackgroundImage(condition)

                }
            }

            override fun onFailure(call: Call<WeatherAppData>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Not Found", Toast.LENGTH_SHORT).show()
            }

        }
        )
    }

    private fun cardClicks(
        humidity: String? = null,
        windSpeed: String? = null,
        sunRise: String? = null,
        sunSet: String? = null,
        rainMeter: String? = null,
        seaLevel: String? = null
    ) {
        binding.humidityLinearLayout.setOnClickListener {
            startActivity(
                Intent(this, CategoriesDetailedActivity::class.java).putExtra(
                    "humidity",
                    humidity
                )
            )
        }
        binding.windSpeedLinearLayout.setOnClickListener {
            startActivity(
                Intent(this, CategoriesDetailedActivity::class.java).putExtra(
                    "windSpeed",
                    windSpeed
                )
            )
        }
        binding.sunriseLinearLayout.setOnClickListener {
            startActivity(
                Intent(this, CategoriesDetailedActivity::class.java).putExtra(
                    "sunRise",
                    sunRise
                )
            )
        }
        binding.sunsetLinearLayout.setOnClickListener {
            startActivity(
                Intent(this, CategoriesDetailedActivity::class.java).putExtra(
                    "sunSet",
                    sunSet
                )
            )
        }
        binding.conditionLinearLayout.setOnClickListener {
            startActivity(
                Intent(this, CategoriesDetailedActivity::class.java).putExtra(
                    "rainMeter",
                    rainMeter
                )
            )
        }
        binding.seaLinearLayout.setOnClickListener {
            startActivity(
                Intent(this, CategoriesDetailedActivity::class.java).putExtra(
                    "seaLevel",
                    seaLevel
                )
            )
        }
    }

    fun dayName(timeStamp: Long): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date())
    }

    fun time(timeStamp: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(timeStamp * 1000))
    }

    fun date(): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    fun changeBackgroundImage(condition: String) {
        when (condition) {
            "Clear Sky", "Sunny", "Clear" -> {
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }

            "Partly Clouds", "Clouds", "Overcast", "Mist", "Foggy", "Smoke", "Haze" -> {
                binding.root.setBackgroundResource(R.drawable.colud_background)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)
            }

            "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain", "Rain" -> {
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView.setAnimation(R.raw.rain)
            }

            "Light Snow", "Moderate Snow", "Heavy Snow", "Blizzard", "Snow" -> {
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.lottieAnimationView.setAnimation(R.raw.snow)
            }

            else -> {
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
        }
        binding.lottieAnimationView.playAnimation()
    }

}