package com.example.quitsmokingnow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quitsmokingnow.adapter.ListReasonAdapter
import com.example.quitsmokingnow.database.ListReasonDatabase
import com.example.quitsmokingnow.databinding.ActivityFirstIntroduceBinding
import com.example.quitsmokingnow.model.ListReasonQuitSmoking

class FirstIntroduce : AppCompatActivity() {
    lateinit var mList:List<ListReasonQuitSmoking>;
    lateinit var binding:ActivityFirstIntroduceBinding;

    lateinit var listReasonAdapter: ListReasonAdapter;
    lateinit var linearLayoutManager: LinearLayoutManager;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstIntroduceBinding.inflate(layoutInflater);
        setContentView(binding.root)
        binding.btnGo.setOnClickListener {
            go();
        }
        binding.btnAdd.setOnClickListener{
            addReason();
        }
        initView();
    }

    private fun initView() {
        // display to recyclerview
        mList = ListReasonDatabase.getDatabase(this)?.mListReasonDAO()?.getAll()!!;
        listReasonAdapter = ListReasonAdapter();
        linearLayoutManager = LinearLayoutManager(this);
        listReasonAdapter.setData(mList);
        binding.recyclerView.layoutManager = linearLayoutManager;
        binding.recyclerView.adapter = listReasonAdapter;
    }
    private fun addReason(){
        var reason = binding.edtGetReason.text.toString().trim();
        if(TextUtils.isEmpty(reason)){
            return;
        }
        var mReason = ListReasonQuitSmoking(null,reason);
        //insert to database
        ListReasonDatabase.getDatabase(this)?.mListReasonDAO()?.insertAll(mReason);
    }

    private fun go(){
        var intent = Intent(this,SecondIntroduce::class.java);
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        finish();
    }
}