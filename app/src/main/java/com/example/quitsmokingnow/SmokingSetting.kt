package com.example.quitsmokingnow

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.quitsmokingnow.database.DetaiSmokingDatabase
import com.example.quitsmokingnow.databinding.ActivitySmokingSettingBinding
import com.example.quitsmokingnow.model.DetailSmokingEntity
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import java.time.LocalDateTime




class SmokingSetting : AppCompatActivity() , DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener{
    var hour: Int = 0
    var minute: Int = 0
    var myDay = ""
    var myMonth: String = ""
    var myYear: Int = 0
    var myHour: String = ""
    var myMinute: String = ""
    var mFulltime:String ="";

    var milis:Long = 0;
    private lateinit var binding: ActivitySmokingSettingBinding;
    private lateinit var  list: List<DetailSmokingEntity>;
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmokingSettingBinding.inflate(layoutInflater);
        setContentView(binding.root);
        initView();
        binding.back.setOnClickListener{
            var intent = Intent(this,MainActivity::class.java);
            startActivity(intent);
            finish()
        }
        binding.update.setOnClickListener{
            updatePrice();
        }
    }

    private fun initView(){
        list = DetaiSmokingDatabase.getDatabase(this)?.mDetailDao()?.getAll()!!
        binding.txtDate.text = list[0].time.substring(0,10)
        binding.txtTime.text = list[0].time.substring(11)
        binding.amount.setText(list[0].amount)
        binding.price.setText(list[0].price)

        binding.choosedate.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            val datePickerDialog =
                DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE))
            datePickerDialog.show()
        }
        binding.choosetime.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            val datePickerDialog =
                DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE))
            datePickerDialog.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updatePrice(){
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val date = binding.txtDate.text;
        val time = binding.txtTime.text;

        var listinfor :List<DetailSmokingEntity> = DetaiSmokingDatabase.getDatabase(this)?.mDetailDao()?.getAll()!!;
        var detail = listinfor[0];


        var price: String =  binding.price.text.toString().trim();
        detail.price = "${price}"

        var amount = binding.amount.text.toString().trim();
        detail.amount = "${amount}";



        val time_edited = LocalDateTime.parse("${date} ${time}", formatter);
        val time_unrectified = LocalDateTime.parse(listinfor[0].time, formatter);

        milis = (java.time.Duration.between(time_unrectified,time_edited).toMillis());
        if(milis >=0){
            detail.time =  "${date} ${time}";
            DetaiSmokingDatabase.getDatabase(this)?.mDetailDao()?.update(detail)!!;
            var intent = Intent(this,MainActivity::class.java);
            startActivity(intent);
            finish();
        }else{
           Toast.makeText(this,"Modified time must be greater than previously set time",Toast.LENGTH_SHORT).show();
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        myYear = p1
        myMonth = "${p2+1}"; // month start from 0 to 11 instead of 1->12
        myDay =  "${p3}"

        if(p3 < 10){
            myDay = "0${p3}"
        }
        if((p2+1) < 10){
            myMonth = "0${p2+1}";
        }
        binding.txtDate.text = "${myYear}-${myMonth}-${myDay}"
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this, this, hour, minute,
            DateFormat.is24HourFormat(this)
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        if(p1<10){
            myHour = "0${p1}";
        }else {
            myHour = "${p1}";
        }

        if(p2<10){
            myMinute = "0${p2}";
        }else{
            myMinute = "${p2}";
        }
        binding.txtTime.text = "${myHour}:${myMinute}"
        // convert time 12hours to 24hours

        val calendar = Calendar.getInstance()
        calendar.set(0,0,0,p1,p2)

        mFulltime = "${myYear}-${myMonth}-${myDay} ${myHour}:${myMinute}";
        binding.txtDate.text = "${myYear}-${myMonth}-${myDay}"
        binding.txtTime.text = "${myHour}:${myMinute}"
    }
}