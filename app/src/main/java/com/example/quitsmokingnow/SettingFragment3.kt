package com.example.quitsmokingnow

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.quitsmokingnow.database.DetaiSmokingDatabase
import com.example.quitsmokingnow.databinding.FragmentSetting3Binding
import com.example.quitsmokingnow.model.DetailSmokingEntity
import androidx.core.content.ContextCompat.getSystemService

import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.MotionEvent

import androidx.core.content.ContextCompat.getSystemService





class SettingFragment3 : Fragment() {
    private lateinit var binding:FragmentSetting3Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetting3Binding.inflate(layoutInflater);
        binding.next.setOnClickListener{
            updatePrice();
        }
        return binding.root;
    }
    private fun updatePrice(){
        var listinfor :List<DetailSmokingEntity> = DetaiSmokingDatabase.getDatabase(activity?.applicationContext!!)?.mDetailDao()?.getAll()!!;
        var detail = listinfor[0];
        if(TextUtils.isEmpty(binding.edtPrice.toString().trim())){
          return;
        }
        var price: String =  binding.edtPrice.text.toString().trim();
        detail.price = "${price.toDouble() / binding.chooseCriInpack.value}"
        DetaiSmokingDatabase.getDatabase(activity?.applicationContext!!)?.mDetailDao()?.update(detail)!!;
        var intent = Intent(activity?.applicationContext,MainActivity::class.java);
        startActivity(intent);
    }
}