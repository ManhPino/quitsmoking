package com.example.quitsmokingnow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quitsmokingnow.adapter.ListReasonAdapter
import com.example.quitsmokingnow.database.ListReasonDatabase
import com.example.quitsmokingnow.databinding.ActivityFirstIntroduceBinding
import com.example.quitsmokingnow.model.ListReasonQuitSmoking
import com.example.quitsmokingnow.viewmodel.ListReasonViewModel
import org.intellij.lang.annotations.JdkConstants

class FirstIntroduce : AppCompatActivity() {
    lateinit var binding:ActivityFirstIntroduceBinding;

    lateinit var listReasonAdapter: ListReasonAdapter;
    lateinit var linearLayoutManager: LinearLayoutManager;

    private lateinit var listReasonViewModel: ListReasonViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstIntroduceBinding.inflate(layoutInflater);
        setContentView(binding.root)

        initView();

        binding.btnGo.setOnClickListener {
            go();
        }
        binding.btnAdd.setOnClickListener{
            addReason();
        }
    }

    private fun initView() {

        listReasonViewModel = ViewModelProvider(this)[ListReasonViewModel::class.java];

        // setup adapter for recycler view
        listReasonAdapter = ListReasonAdapter();
        linearLayoutManager = LinearLayoutManager(this);
        binding.recyclerView.layoutManager = linearLayoutManager;

        // view model observe
        listReasonViewModel.getReasonViewModel(this).observe(this, Observer {
            listReasonAdapter.setData(it);
            binding.recyclerView.adapter = listReasonAdapter;
        });
    }
    private fun addReason(){
        var reason = binding.edtGetReason.text.toString().trim();
        if(TextUtils.isEmpty(reason)){
            return;
        }
        var mReason = ListReasonQuitSmoking(null,reason);
        listReasonViewModel.insertData(mReason);
    }

    private fun go(){
        var intent = Intent(this,SecondIntroduce::class.java);
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        finish();
    }
}