package com.example.quitsmokingnow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quitsmokingnow.dao.ListReasonDao
import com.example.quitsmokingnow.model.ListReasonQuitSmoking

@Database(entities = [ListReasonQuitSmoking::class], version = 1)
abstract class ListReasonDatabase: RoomDatabase() {
    companion object{
         var DATABASE_NAME:String = "QuitSmokingApp.db";
         var INSTANCE:ListReasonDatabase ?= null;
         fun getDatabase(context:Context):ListReasonDatabase?{
             if(INSTANCE == null){
                 INSTANCE =  Room.databaseBuilder(context.applicationContext,ListReasonDatabase::class.java, DATABASE_NAME)
                     .allowMainThreadQueries().build();
             }
             return INSTANCE;
         }
    }
    abstract fun mListReasonDAO(): ListReasonDao
}