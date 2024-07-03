package com.example.appchinhthuc.Activity

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.activity.ComponentActivity
import com.example.appchinhthuc.R
import com.example.appchinhthuc.databinding.CharViewBinding

class ChartCoin :ComponentActivity() {

    private var _binding:CharViewBinding?=null
    private val binding get() =_binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=CharViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            lineChart.gradientFillColors     = intArrayOf(
                Color.parseColor("#c9454f"),
                Color.TRANSPARENT
            )
            lineChart.animation.duration= animationDuration
            lineChart.animate(lineSet)
        }
    }
    companion object{
        private val lineSet= listOf(
            "label1" to 1F,
            "label2" to 3F,
            "label3" to 3.5F,
            "label4" to 3F,
            "label5" to 5F,
            "label6" to 8F,
            "label6" to 7F,
            "label6" to 7.5F,
            "label6" to 7F,
            "label6" to 5F,
            "label6" to 6F,
            "label6" to 6F,
            "label6" to 7F,
            "label1" to 7.5F,
            "label2" to 8F,
            "label3" to 10F,
            "label4" to 9F,
            "label5" to 8.5F,
            "label6" to 8F,
            "label6" to 9F,
            "label6" to 7F,
            "label6" to 5F,
            "label6" to 6F,
            "label6" to 7F,

        )
        private const val animationDuration=1000L
    }
}