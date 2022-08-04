package com.example.quitsmokingnow

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.quitsmokingnow.databinding.FragmentSetting2Binding
import com.example.quitsmokingnow.model.DetailSmokingEntity
import com.example.quitsmokingnow.viewmodel.DetailSmokingViewModel
import java.text.SimpleDateFormat
import java.util.*



class SettingFragment2 : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    var hour: Int = 0
    var minute: Int = 0
    var myDay = ""
    var myMonth: String = ""
    var myYear: Int = 0
    var myHour: String = ""
    var myMinute: String = ""
    var mFulltime:String ="";
    private lateinit var binding: FragmentSetting2Binding;
    lateinit var detailSmokingViewModel: DetailSmokingViewModel;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetting2Binding.inflate(layoutInflater);
        initView();
        //
        binding.btnGoEnd.setOnClickListener{
            insertInforSmoking();
        }
        //
        return binding.root;
    }
    private fun initView(){
        detailSmokingViewModel = ViewModelProvider(this).get(DetailSmokingViewModel::class.java)
        detailSmokingViewModel.getDetailViewModel(activity?.applicationContext!!).observe(viewLifecycleOwner,{

        })
        mFulltime = SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date())
        binding.txtDate.text = SimpleDateFormat("yyyy-MM-dd").format(Date())
        binding.txtTime.text = SimpleDateFormat("HH:mm").format(Date())

        binding.chooseDate.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            val datePickerDialog =
                DatePickerDialog(requireContext(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE))
            datePickerDialog.show()
        }
        binding.chooseTime.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            val datePickerDialog =
                DatePickerDialog(requireContext(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE))
            datePickerDialog.show()
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
        val timePickerDialog = TimePickerDialog(activity, this, hour, minute,
            DateFormat.is24HourFormat(activity)
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
    private fun insertInforSmoking(){
        var amount = binding.chooseCriSmoke.value.toString();
        var detail = DetailSmokingEntity(1,"user",amount,"",mFulltime)
        detailSmokingViewModel.insertData(detail);
        Navigation.findNavController(binding.root).navigate(R.id.action_settingFragment2_to_settingFragment3)
    }
}