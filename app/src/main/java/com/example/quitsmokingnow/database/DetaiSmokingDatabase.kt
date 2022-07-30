package com.example.quitsmokingnow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quitsmokingnow.dao.DetailSmokingDao
import com.example.quitsmokingnow.model.DetailSmokingEntity

@Database(entities = [DetailSmokingEntity::class], version = 1)
abstract class DetaiSmokingDatabase: RoomDatabase() {
    companion object{
        var DATABASE_NAME:String = "QuitSmokingApp1.db";
        var INSTANCE:DetaiSmokingDatabase ?= null;
        fun getDatabase(context: Context):DetaiSmokingDatabase?{
            if(INSTANCE == null){
                INSTANCE =  Room.databaseBuilder(context.applicationContext,DetaiSmokingDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
            }
            return INSTANCE;
        }
    }
    abstract fun mDetailDao(): DetailSmokingDao
}