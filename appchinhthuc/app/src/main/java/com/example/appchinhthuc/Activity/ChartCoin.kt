package com.example.appchinhthuc.Activity

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.graphics.green
import com.db.williamchart.Grid
import com.db.williamchart.data.AxisType
import com.db.williamchart.data.AxisType.*
import com.db.williamchart.data.shouldDisplayAxisX
import com.db.williamchart.view.LineChartView
import com.example.appchinhthuc.Activity.Model.CryptoLastTrade
import com.example.appchinhthuc.R
import com.example.appchinhthuc.databinding.CharViewBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.transition.MaterialSharedAxis.Axis
import okhttp3.OkHttpClient
import okhttp3.Request
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException
import java.time.LocalDate

class ChartCoin :ComponentActivity() {
    val apiKey = "84d94cc1-3a70-40cc-aec4-a013795092b3"
    private lateinit var coin: CryptoLastTrade
    private var _binding:CharViewBinding?=null
    private val binding get() =_binding!!
    @RequiresApi(Build.VERSION_CODES.O)
    val currenDate=LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=CharViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCryptoLastTrade(apiKey, "BTC", "USD")
        setValue()
        setSpinner()
        drawLineChart()
        setTopIcon()
    }

    private fun drawLineChart(){
        var lineChart=binding.CoinLineChart
        var description=Description()
        description.text="Value Record"
        description.textSize=15f
        description.textColor=Color.WHITE
        lineChart.description=description
        lineChart.axisRight.setDrawLabels(false)

        var xValue= arrayOf("","Jul-1","Jul-15","Jul-30")
        var xAxis:XAxis=lineChart.xAxis
        xAxis.position=XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter=object : ValueFormatter(){
            override fun getFormattedValue(value: Float): String {
                return if((value.toInt()>value||value.toInt()<value)&&value==0f) {
                    ""
                } else{
                    xValue[value.toInt()]
                }
            }
        }
        xAxis.granularity=1f
        xAxis.textColor=resources.getColor(R.color.white)
        xAxis.setDrawGridLines(false)
        xAxis.setAvoidFirstLastClipping(true) // Optional: Ensure first and last labels are not clipped

        var yAxis:YAxis=lineChart.axisLeft
        yAxis.axisMinimum=0f
        yAxis.axisMaximum=100f
        yAxis.axisLineColor=resources.getColor(R.color.white)
        yAxis.textColor=resources.getColor(R.color.white)

        val entries= mutableListOf<Entry>()
        entries.add(Entry(0f,10f))
        entries.add(Entry(0.25f,20f))
        entries.add(Entry(0.5f,18f))
        entries.add(Entry(0.75f,19f))
        entries.add(Entry(1f,30f))
        entries.add(Entry(1.25f,25f))
        entries.add(Entry(1.5f,65f))
        entries.add(Entry(1.75f,60f))
        entries.add(Entry(2f,80f))
        entries.add(Entry(2.25f,50f))
        entries.add(Entry(2.5f,30f))
        entries.add(Entry(2.75f,35f))
        entries.add(Entry(3f,40f))

        val lineDataSet=LineDataSet(entries,"USD")
        lineDataSet.color=resources.getColor(R.color.red)
        lineDataSet.lineWidth=2f
        lineDataSet.valueTextColor=resources.getColor(R.color.white)
        val lineData=LineData(lineDataSet)

        lineChart.legend.textColor=Color.YELLOW
        lineChart.legend.textSize=12f
        lineChart.data=lineData
        lineChart.animateXY(1000,1000)


        lineChart.invalidate()
    }
    fun getCryptoLastTrade(apiKey: String, fromCurrency: String, toCurrency: String) {
        val url = "https://api.polygon.io/v1/last/crypto/$fromCurrency/$toCurrency?apiKey=$apiKey"

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.use {
                    if (!response.isSuccessful) {
                        println("Unexpected code $response")
                        return
                    }
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val jsonAdapter = moshi.adapter(CryptoLastTrade::class.java)
                    val cryptoLastTrade = jsonAdapter.fromJson(response.body!!.string())

                    if (cryptoLastTrade != null) {
                        binding.lastPrice.setText("${cryptoLastTrade.last.price}")
                    }
                    else Toast.makeText(this@ChartCoin, "No access", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setValue(){
        binding.lastPrice.setText("56,655.26 $")
        binding.lastDifference.setText("-6.58")
        if(binding.lastDifference.text.toString().toDouble()<0){
            binding.lastDifference.setTextColor(getColor(R.color.red))
        }
        else binding.lastDifference.setTextColor(getColor(R.color.green))
        binding.dateLastTrade.setText("${currenDate.toString()}")
        binding.NameCoinQuestion.setText("Bitcoin")
    }
    private fun setSpinner(){
        val spiner=findViewById<Spinner>(R.id.idCurrency)
        if(spiner!=null) {
            ArrayAdapter.createFromResource(
                this,
                R.array.Currency,
                R.layout.style_item_spinner,
            ).also { adapter->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spiner.adapter=adapter
            }
            spiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // Get selected item
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    // Display it in a Toast
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Another interface callback
                }
            }
        }
    }
    private fun setTopIcon(){
        var d = R.drawable.star
        var p=R.drawable.star_cheked

        var e = R.drawable.share_icon
        var f=R.drawable.forward

        var star=findViewById<ImageView>(R.id.favorImage)
            star.setOnClickListener{
                star.setImageResource(d)
                var q=d
                d=p
                p=q
            }
        var share=findViewById<ImageView>(R.id.shareImage)
        share.setOnClickListener{
            share.setImageResource(e)
            var g=e
            e=f
            f=g
        }
    }
}