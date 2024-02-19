package com.example.atmosvista.categoriesActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.atmosvista.R
import com.example.atmosvista.databinding.ActivityCategoriesDetailedBinding

class CategoriesDetailedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriesDetailedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val humidity = intent.getStringExtra("humidity")
        val windSpeed = intent.getStringExtra("windSpeed")
        val sunRise = intent.getStringExtra("sunRise")
        val sunSet = intent.getStringExtra("sunSet")
        val condition = intent.getStringExtra("rainMeter")
        val seaLevel = intent.getStringExtra("seaLevel")

        humidityMain(humidity)
        windSpeedMain(windSpeed)
        sunRiseCheck(sunRise)
        sunSetCheck(sunSet)
        conditionCheck(condition)
        seaLevelCheck(seaLevel)

    }

    //Done, check anim?
    //Total anim change
    private fun seaLevelCheck(seaLevel: String?) {
        if (seaLevel != null) {
            binding.detailedLottieAnimation.setAnimation(R.raw.sealevel)
            binding.detailedInfoText.text = seaLevel
            binding.topHeadingText.text = "SeaLevel"
            binding.categoryText.text = "SeaLevel"
            binding.root.setBackgroundResource(R.drawable.my_sealevel)

            val countSeaLevel = seaLevel.toInt()

            if (countSeaLevel <= 998) {
                binding.detailedQuoteText.text =
                    "The sea level is so low, even submarines are hitting speed bumps"
            } else {
                binding.detailedQuoteText.text =
                    "I asked the sea level how it's doing, and it just gave me a high tide"
            }
            binding.detailedLottieAnimation.playAnimation()
        }
    }

    //Done
    private fun conditionCheck(condition: String?) {
        if (condition != null) {
            binding.detailedLottieAnimation.setAnimation(R.raw.windspeed)
            binding.detailedInfoText.text = condition
            binding.topHeadingText.text = "Condition"
            binding.categoryText.text = "Condition"

            when (condition) {
                "Clear Sky", "Sunny", "Clear" -> {
                    binding.root.setBackgroundResource(R.drawable.sunny_background)
                    binding.detailedLottieAnimation.setAnimation(R.raw.sun)
                }

                "Partly Clouds", "Clouds", "Overcast", "Mist", "Foggy", "Smoke", "Haze" -> {
                    binding.root.setBackgroundResource(R.drawable.colud_background)
                    binding.detailedLottieAnimation.setAnimation(R.raw.cloud)
                }

                "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain", "Rain" -> {
                    binding.root.setBackgroundResource(R.drawable.rain_background)
                    binding.detailedLottieAnimation.setAnimation(R.raw.rain)
                }

                "Light Snow", "Moderate Snow", "Heavy Snow", "Blizzard", "Snow" -> {
                    binding.root.setBackgroundResource(R.drawable.snow_background)
                    binding.detailedLottieAnimation.setAnimation(R.raw.snow)
                }

                else -> {
                    binding.root.setBackgroundResource(R.drawable.sunny_background)
                    binding.detailedLottieAnimation.setAnimation(R.raw.sun)
                }
            }
        }
    }

    //Done, check anim?
    private fun sunSetCheck(sunSet: String?) {
        if (sunSet != null) {
            binding.detailedLottieAnimation.setAnimation(R.raw.check3)
            binding.root.setBackgroundResource(R.drawable.my_sunset)
            binding.detailedInfoText.text = sunSet
            binding.detailedQuoteText.text =
                "Sunsets are proof that no matter what happens, every day can end beautifully"
            binding.topHeadingText.text = "SunSet"
            binding.categoryText.text = "SunSet"
        }
    }

    //Done, check anim?
    private fun sunRiseCheck(sunRise: String?) {
        if (sunRise != null) {
            binding.detailedLottieAnimation.setAnimation(R.raw.check2)
            binding.root.setBackgroundResource(R.drawable.my_sunrise)
            binding.detailedInfoText.text = sunRise
            binding.detailedQuoteText.text = "A sunrise is God's way of saying, 'Let's start again"
            binding.topHeadingText.text = "SunRise"
            binding.categoryText.text = "SunRise"
        }
    }

    //Done, check background?
    private fun windSpeedMain(windSpeed: String?) {
        if (windSpeed != null) {
            binding.detailedLottieAnimation.setAnimation(R.raw.windspeed)
            binding.detailedInfoText.text = windSpeed
            binding.topHeadingText.text = "WindSpeed"
            binding.categoryText.text = "WindSpeed"

            val countWindSpeed = windSpeed.toDouble()

            if (countWindSpeed <= 5.0) {
                binding.root.setBackgroundResource(R.drawable.my_windspeed)
                binding.detailedQuoteText.text =
                    "This wind is so calm, even flags are taking a nap"
            } else if (countWindSpeed in 5.1..10.0) {
                binding.root.setBackgroundResource(R.drawable.my_windspeed)
                binding.detailedQuoteText.text =
                    "The wind is so gentle today, it's like nature's way of saying, 'Take it easy, folks"
            } else if (countWindSpeed in 10.1..15.0) {
                binding.root.setBackgroundResource(R.drawable.my_windspeed2)
                binding.detailedQuoteText.text =
                    "Windy days are nature's way of telling you to check the strength of your hairstyle"
            } else if (countWindSpeed > 15.0) {
                binding.root.setBackgroundResource(R.drawable.my_windspeed2)
                binding.detailedQuoteText.text = "where bad hair days become epic hair adventures"
            }
            binding.detailedLottieAnimation.playAnimation()
        }
    }

    private fun humidityMain(humidity: String?) {
        if (humidity != null) {
            binding.detailedInfoText.text = humidity
            binding.topHeadingText.text = "Humidity"
            binding.categoryText.text = "Humidity"

            val countHumidity = humidity.toInt()

            if (countHumidity <= 25) {
                binding.root.setBackgroundResource(R.drawable.my_windspeed)
                binding.detailedLottieAnimation.setAnimation(R.raw.sun)
                binding.detailedQuoteText.text =
                    "Humidity so low, even my plants are using moisturizer"
            } else if (countHumidity in 26..49) {
                binding.root.setBackgroundResource(R.drawable.my_windspeed)
                binding.detailedLottieAnimation.setAnimation(R.raw.sun)
                binding.detailedQuoteText.text =
                    "Humidity is like the chill friend at the party – not too intense, just adding a bit of atmosphere to the air"
            } else if (countHumidity in 50..74) {
                binding.root.setBackgroundResource(R.drawable.my_humidity)
                binding.detailedLottieAnimation.setAnimation(R.raw.humidity)
                binding.detailedQuoteText.text =
                    "Humidity: nature's way of telling you to embrace the frizz"
            } else if (countHumidity > 75) {
                binding.root.setBackgroundResource(R.drawable.my_humidity)
                binding.detailedLottieAnimation.setAnimation(R.raw.humidity)
                binding.detailedQuoteText.text =
                    "Humidity is like a clingy ex – it just won't let you breathe"
            }
            binding.detailedLottieAnimation.playAnimation()
        }
    }
}