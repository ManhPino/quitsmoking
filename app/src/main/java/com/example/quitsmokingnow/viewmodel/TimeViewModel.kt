package com.example.quitsmokingnow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quitsmokingnow.model.TimeUnit

class TimeViewModel: ViewModel() {
    private  var timeViewModel: MutableLiveData<TimeUnit> = MutableLiveData()

    fun setTime(timeUnit: TimeUnit){
        timeViewModel.postValue(timeUnit)
    }
    fun getTimeViewModel():MutableLiveData<TimeUnit>{
        return timeViewModel
    }
}