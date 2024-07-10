package com.example.appchinhthuc.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.appchinhthuc.R
import com.example.appchinhthuc.databinding.ActivityDetailCryptoBinding
import com.example.appchinhthuc.databinding.UserProfileBinding

class UserProfile :ComponentActivity() {
    private var _binding:UserProfileBinding?=null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=UserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDirectionBtn()
        eyeClick()
    }
    private fun eyeClick(){
        var phoneNum=findViewById<TextView>(R.id.phone_num)
        var balance=findViewById<TextView>(R.id.balance)
        val setEye=findViewById<ImageView>(R.id.eye)
        setEye.setOnClickListener {
            if (balance.text.toString() == "- - - - - - - -") {
                balance.setText("150,548.11")
                phoneNum.setText("0916044262")
                setEye.setImageResource(R.drawable.eye_open)
            } else {
                balance.setText("- - - - - - - -")
                phoneNum.setText("*******262")
                setEye.setImageResource(R.drawable.hide_infor_icon)
            }
        }
    }
    private fun setDirectionBtn(){
        binding.HomeBtn.setOnClickListener{
            val intent= Intent(this@UserProfile,MainActivity::class.java)
            startActivity(intent)
        }
        binding.ExplorerBtn.setOnClickListener{
            val intent= Intent(this@UserProfile,MainActivity::class.java)
            startActivity(intent)
        }
        binding.BookmarkBtn.setOnClickListener{
            val intent= Intent(this@UserProfile,MainActivity::class.java)
            startActivity(intent)
        }
        binding.ChartBtn.setOnClickListener{
            val intent= Intent(this@UserProfile,ChartCoin::class.java)
            startActivity(intent)
        }
        binding.ProfileBtn.setOnClickListener{
            val intent= Intent(this@UserProfile,UserProfile::class.java)
            startActivity(intent)
        }
    }
}