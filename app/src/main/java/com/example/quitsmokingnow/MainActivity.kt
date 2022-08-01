package com.example.quitsmokingnow

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quitsmokingnow.database.DetaiSmokingDatabase
import com.example.quitsmokingnow.databinding.ActivityMainBinding
import com.example.quitsmokingnow.model.DetailSmokingEntity
import com.example.quitsmokingnow.model.TimeUnit
import com.example.quitsmokingnow.viewmodel.DetailSmokingViewModel
import com.example.quitsmokingnow.viewmodel.ListReasonViewModel
import com.example.quitsmokingnow.viewmodel.TimeViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    private lateinit var detailSmokingViewModel: DetailSmokingViewModel;
    private lateinit var  list: List<DetailSmokingEntity>
    private lateinit var formatter:DateTimeFormatter
    private lateinit var date_start: LocalDateTime;
    private lateinit var date_now:LocalDateTime;

    private lateinit var timeViewModel: TimeViewModel
     var milis:Long = 0;
     var minitues:Int = 0;
     var hours:Int = 0;
     var days:Int = 0;
     lateinit var timeUnit:TimeUnit ;

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);
        initView()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView(){

       // DetaiSmokingDatabase.getDatabase(this)?.mDetailDao()?.deleteAllDetail()
        
        timeViewModel = ViewModelProvider(this)[TimeViewModel::class.java]
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

        // get list from room-database
        list = DetaiSmokingDatabase.getDatabase(this)?.mDetailDao()?.getAll()!!;

        if(list.size == 0){
           val intent =  Intent(this,DefaultActivity::class.java);
           startActivity(intent);
        }else if(list.size>0){
            Log.d("AAA",list.toString())

            startCaculatorTime() // run hour counter with every update is 30 second

            // call viewmodel and update lên ui ux
            timeViewModel.getTimeViewModel().observe(this,{
                binding.minites.text = "${it.minites}";
                binding.hours.text = "${it.hours}";
                binding.days.text = "${it.days}";
                var total_hours = it.days * 24 + it.hours ;
                _getInforUser(total_hours);
            })
        }
    }
    private fun startCaculatorTime(){
        Timer().schedule(object : TimerTask() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun run() {
                _getTime();
            }
        }, 0,30000)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun _getTime(){
       var time_start:String = list[0].time; // get time start quit smoking from RoomDatabase
        val time_current:String = LocalDateTime.now()
            .format(DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm")); // get time current from system

        date_start = LocalDateTime.parse(time_start,formatter);
        date_now = LocalDateTime.parse(time_current,formatter);

        // caculator distance time-start to time-current with type Integer;
        milis = (java.time.Duration.between(date_start,date_now).toMillis());



        minitues = Integer.parseInt("${(milis/1000/60)%60}")
        hours = Integer.parseInt("${(milis/1000/60/60)%24}");
        days = Integer.parseInt("${milis/1000/60/60/24}");

        //call ViewModel and Update ;
         timeUnit = TimeUnit(minitues,hours,days);
         timeViewModel.setTime(timeUnit);

    }
    private fun _getInforUser(total_hours:Int){

        var ciri =  (list[0].amount).toFloat();
        var price = (list[0].price).toFloat();


        var wonback = 24 - hours;
        var dayquit = days;
        var monney_saved = (price * ciri) / 24 * total_hours;
        var crigated_avoid = ciri / 24 * total_hours;



        binding.dayquits.text = "${dayquit}";
        binding.moneySave.text = "${monney_saved.toInt()}"
        binding.criAvoid.text = "${crigated_avoid.toInt()}"
        binding.wonback.text = "${wonback}"
    }
}