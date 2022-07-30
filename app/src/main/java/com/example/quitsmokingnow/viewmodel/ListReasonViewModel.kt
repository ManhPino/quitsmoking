package com.example.quitsmokingnow.viewmodel


import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quitsmokingnow.database.ListReasonDatabase
import com.example.quitsmokingnow.model.ListReasonQuitSmoking

class ListReasonViewModel(): ViewModel() {
    private  lateinit var context:Context;
    private  var allReason: MutableLiveData<List<ListReasonQuitSmoking>> = MutableLiveData()
    private  var listReason: List<ListReasonQuitSmoking> = emptyList()


     fun getAllReason(){
        listReason = ListReasonDatabase.getDatabase(context.applicationContext)?.mListReasonDAO()?.getAll()!!;
        allReason.value = listReason;
    }
     fun insertData(mReason:ListReasonQuitSmoking){
      ListReasonDatabase.getDatabase(this.context.applicationContext)?.mListReasonDAO()?.insertAll(mReason);
         getAllReason();
    }
     fun getReasonViewModel(context: Context):MutableLiveData<List<ListReasonQuitSmoking>> {
         this.context = context;
         getAllReason()
         return allReason;
    }

}