package com.example.quitsmokingnow.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quitsmokingnow.dao.DetailSmokingDao
import com.example.quitsmokingnow.database.DetaiSmokingDatabase
import com.example.quitsmokingnow.model.DetailSmokingEntity

class DetailSmokingViewModel : ViewModel(){
    private lateinit var context: Context;
    private var allDetailSmokingViewModel: MutableLiveData<List<DetailSmokingEntity>> = MutableLiveData();
    private var listDetail:List<DetailSmokingEntity> = listOf();

    private fun getAllDetail(){
        listDetail =
            DetaiSmokingDatabase.getDatabase(context.applicationContext)?.mDetailDao()?.getAll()!!;
        allDetailSmokingViewModel.postValue(listDetail);
    }
    fun insertData(detail:DetailSmokingEntity){
        DetaiSmokingDatabase.getDatabase(context.applicationContext)?.mDetailDao()?.insertDetail(detail)!!;
        getAllDetail();
    }
     fun getDetailViewModel(context: Context):MutableLiveData<List<DetailSmokingEntity>>{
       this.context = context;
        getAllDetail();
        return allDetailSmokingViewModel;
    }
}