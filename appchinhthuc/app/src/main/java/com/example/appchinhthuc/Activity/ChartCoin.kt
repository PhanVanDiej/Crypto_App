package com.example.appchinhthuc.Activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.graphics.green
import com.db.williamchart.Grid
import com.db.williamchart.data.AxisType
import com.db.williamchart.data.AxisType.*
import com.db.williamchart.data.shouldDisplayAxisX
import com.db.williamchart.view.LineChartView
import com.example.appchinhthuc.Activity.Model.CoinMarketCapResponse
import com.example.appchinhthuc.Activity.Model.CryptoCurrency
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

class ChartCoin :ComponentActivity() {
    private var _binding:CharViewBinding?=null
    private val binding get() =_binding!!
    @RequiresApi(Build.VERSION_CODES.O)
    val currenDate=LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=CharViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )
        setSpinner()
        setChangedDuration()
        drawLineChart()
        setTopIcon()
        HomeBtnClick()
        ExplorerBtnClick()
        BookmarkBtnClick()
        ProfileBtnClick()
        fetchLatestListings()
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
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setValue(data :CryptoCurrency){
        var price=data.quote.USD.price
        price=BigDecimal(price).setScale(2,RoundingMode.HALF_EVEN).toDouble()
        binding.lastPrice.setText("$ ${price}")
        var changed=data.quote.USD.percent_change_1h
        changed=BigDecimal(changed).setScale(2,RoundingMode.HALF_EVEN).toDouble()
        binding.lastDifference.setText("${changed}")
        val duration=binding.ChangedDuration
        duration.onItemSelectedListener=
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                if(selectedItem=="1H") {
                    var changed=data.quote.USD.percent_change_1h
                    changed=BigDecimal(changed).setScale(2,RoundingMode.HALF_EVEN).toDouble()
                    binding.lastDifference.setText("${changed}")
                    if(changed<0)
                        binding.lastDifference.setTextColor(Color.RED)
                    else binding.lastDifference.setTextColor(Color.GREEN)
                }
                else if(selectedItem=="1D") {
                    var changed=data.quote.USD.percent_change_24h
                    changed=BigDecimal(changed).setScale(2,RoundingMode.HALF_EVEN).toDouble()
                    binding.lastDifference.setText("${changed}")
                    if(changed<0)
                        binding.lastDifference.setTextColor(Color.RED)
                    else binding.lastDifference.setTextColor(Color.GREEN)
                }
                else if(selectedItem=="7D") {
                    var changed=data.quote.USD.percent_change_7d
                    changed=BigDecimal(changed).setScale(2,RoundingMode.HALF_EVEN).toDouble()
                    binding.lastDifference.setText("${changed}")
                    if(changed<0)
                        binding.lastDifference.setTextColor(Color.RED)
                    else binding.lastDifference.setTextColor(Color.GREEN)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        if(binding.lastDifference.text.toString().toDouble()<0){
            binding.lastDifference.setTextColor(getColor(R.color.red))
        }
        else binding.lastDifference.setTextColor(getColor(R.color.green))

        binding.dateLastUpdate.setText("${data.last_updated}")

        binding.NameCoinQuestion.setText("${data.name}")

        binding.nameCoin.setText("${data.name} Price")

        if(data.name=="Ethereum"){
            binding.nameCoin.setTextColor(Color.BLUE)
            binding.lastPrice.setTextColor(Color.BLUE)
            binding.LogoCoin.setImageResource(R.drawable.etherium)
            binding.QFirst.setTextColor(Color.BLUE)
            binding.QBack.setTextColor(Color.BLUE)
        }
        else if(data.name=="Tether USDt"){
            binding.nameCoin.setTextColor(getColor(R.color.Tether_style))
            binding.lastPrice.setTextColor(getColor(R.color.Tether_style))
            binding.LogoCoin.setImageResource(R.drawable.tether)
            binding.QFirst.setTextColor(getColor(R.color.Tether_style))
            binding.QBack.setTextColor(getColor(R.color.Tether_style))
        }
        else if(data.slug=="tron"){
            binding.nameCoin.setTextColor(getColor(R.color.red))
            binding.lastPrice.setTextColor(getColor(R.color.red))
            binding.LogoCoin.setImageResource(R.drawable.trox)
            binding.QFirst.setTextColor(getColor(R.color.red))
            binding.QBack.setTextColor(getColor(R.color.red))
        }
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
            spiner.setSelection(0)
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
    private fun setChangedDuration(){
        val spiner=findViewById<Spinner>(R.id.ChangedDuration)
        if(spiner!=null) {
            ArrayAdapter.createFromResource(
                this,
                R.array.ChangedDuration,
                R.layout.style_item_spinner,
            ).also { adapter->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spiner.adapter=adapter
            }
            spiner.setSelection(0)
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
    private fun HomeBtnClick(){
        findViewById<LinearLayout>(R.id.HomeBtn).setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun ExplorerBtnClick(){
        findViewById<LinearLayout>(R.id.ExplorerBtn).setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun BookmarkBtnClick(){
        findViewById<LinearLayout>(R.id.BookmarkBtn).setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun ProfileBtnClick(){
        findViewById<LinearLayout>(R.id.ProfileBtn).setOnClickListener{
            val intent=Intent(this,UserProfile::class.java)
            startActivity(intent)
        }
    }
    private fun fetchLatestListings() {
        val call = RetrofitInstance.api.getLatestListings(start = 1, limit = 200, convert = "USD")

        call.enqueue(object : Callback<CoinMarketCapResponse> {
            override fun onResponse(call: Call<CoinMarketCapResponse>, response: Response<CoinMarketCapResponse>) {
                if (response.isSuccessful) {
                    response.body()?.data?.let { direcSetValue(it) }
                    // Process the response data

                } else {
                    Toast.makeText(this@ChartCoin,"Error: ${response.errorBody()}",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CoinMarketCapResponse>, t: Throwable) {
                Toast.makeText(this@ChartCoin,"Failed to fetch data: ${t.message}",Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun direcSetValue(dataCurrencyInfo: List<CryptoCurrency>){
        var data=""
        dataCurrencyInfo.forEach {
            data+=" " + it.name
        }
        binding.testdata.setText(data)
        val search=binding.searchField
        dataCurrencyInfo?.forEach {
            if(it.name=="Bitcoin")
                setValue(it)
        }
        search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val inputText = search.text.toString().toLowerCase()
                dataCurrencyInfo?.forEach {
                    if (it.slug == inputText)
                        setValue(it)
                }
                true // Return true to indicate that you have handled the event
            } else {
                false
            }
        })
    }
}