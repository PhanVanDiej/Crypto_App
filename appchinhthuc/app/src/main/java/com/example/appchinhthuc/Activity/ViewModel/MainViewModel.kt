package com.example.appchinhthuc.Activity.ViewModel

import androidx.lifecycle.ViewModel
import com.example.appchinhthuc.Activity.Repository.MainRepository

class MainViewModel(val repository: MainRepository):ViewModel() {
    constructor():this(MainRepository())

    fun loadData()=repository.items
}