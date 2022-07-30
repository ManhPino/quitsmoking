package com.example.quitsmokingnow

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProvider
import com.example.quitsmokingnow.database.DetaiSmokingDatabase
import com.example.quitsmokingnow.databinding.ActivitySecondIntroduceBinding
import com.example.quitsmokingnow.model.DetailSmokingEntity
import com.example.quitsmokingnow.viewmodel.DetailSmokingViewModel
import android.text.format.DateFormat
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.quitsmokingnow.dao.DetailSmokingDao
import com.example.quitsmokingnow.viewmodel.ListReasonViewModel
import java.sql.Time
import java.time.Month
import java.util.*
import java.time.temporal.ChronoField;
import java.time.Instant
import java.time.LocalDateTime


class SecondIntroduce : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: String = ""
    var myMinute: Int = 0
    var mFulltime:String ="";
    lateinit var binding:ActivitySecondIntroduceBinding;
    lateinit var detailSmokingViewModel: DetailSmokingViewModel;
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondIntroduceBinding.inflate(layoutInflater);
        setContentView(binding.root)
        initView()
        binding.btnBack.setOnClickListener {
           back()
        }
        binding.btnGo.setOnClickListener{
            go()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView(){
        // get viewmodel
        detailSmokingViewModel = ViewModelProvider(this).get(DetailSmokingViewModel::class.java)
        detailSmokingViewModel.getDetailViewModel(this).observe(this,{

        });

        // setup time and date picker
        binding.btnChange.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            val datePickerDialog =
                DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE))
            datePickerDialog.show()
        }

    }
    private fun go(){
        var intent = Intent(this,MainActivity::class.java);
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

        //insert data to room database when click button next
        insertInforSmoking()

        // finish current activity
        finish();
    }
    private fun insertInforSmoking(){
        var amount = binding.amount.text.toString().trim();
        var price = binding.price.text.toString().trim()
        if(mFulltime == null){
            return;
        }

        var detail = DetailSmokingEntity(1,"user_manh",amount,price,mFulltime)

        detailSmokingViewModel.insertData(detail);
    }

    // back to firstIntroduce
    private fun back(){
        var intent = Intent(this,FirstIntroduce::class.java);
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        finish();
    }

    // fun OndateSet && onTimeSet get time & date from date-time-picker
    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

        myYear = p1
        myMonth = p2+1; // month start from 0 to 11 instead of 1->12
        myDay =  p3

        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this, this, hour, minute, is24HourFormat(this))
        timePickerDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {

        var mHourSlected = p1;
        if(mHourSlected<10){
            myHour = "0${mHourSlected}";
        }
        myMinute = p2

        // convert time 12hours to 24hours
        val calendar = Calendar.getInstance()
        calendar.set(0,0,0,p1,p2)

        if(myMonth <10){
            mFulltime = "${myYear}-0${myMonth}-${myDay} ${myHour}:${myMinute}";
        }else{
            mFulltime = "${myYear}-${myMonth}-${myDay} ${myHour}:${myMinute}";
        }

        var text_time = "${myDay} ${myMonth} ${myYear} / ${DateFormat.format("hh : mm aaa", calendar) as String}"
        binding.date.text= text_time;
    }
}