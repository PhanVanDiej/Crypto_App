package com.example.appchinhthuc.Activity.Model

import android.os.Parcel
import android.os.Parcelable

data class User(
    val ID: String,
    val UserName: String="",
    val PhoneNumber: String,
    val BankID: String,
    val TotalBalance: Double,
)

