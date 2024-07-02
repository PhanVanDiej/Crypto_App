package com.example.appchinhthuc.Activity

import android.app.Activity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile)

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
}