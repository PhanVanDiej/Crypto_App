package com.example.appchinhthuc.Activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appchinhthuc.Activity.Adapter.CryptoListAdapter
import com.example.appchinhthuc.Activity.ViewModel.MainViewModel
import com.example.appchinhthuc.R
import com.example.appchinhthuc.databinding.ActivityMainBinding
import com.example.appchinhthuc.databinding.ViewholderWalletBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val mainViewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )
        initRecyclerviewCrypto()
        UserBtnClick()
        ChartBtnCLick()
    }
    private fun initRecyclerviewCrypto(){
        binding.view.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.view.adapter=CryptoListAdapter(mainViewModel.loadData());
    }
    private fun UserBtnClick(){
        findViewById<LinearLayout>(R.id.UserBtn).setOnClickListener{
            val intent=Intent(this,UserProfile::class.java)
            startActivity(intent)
        }
    }
    private fun ChartBtnCLick(){
        findViewById<LinearLayout>(R.id.ChartBtn).setOnClickListener{
            val intent=Intent(this,ChartCoin::class.java)
            startActivity(intent)
        }
    }
}