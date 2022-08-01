package com.example.quitsmokingnow

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quitsmokingnow.adapter.ListReasonAdapter
import com.example.quitsmokingnow.databinding.FragmentSetting1Binding
import com.example.quitsmokingnow.model.ListReasonQuitSmoking
import com.example.quitsmokingnow.viewmodel.ListReasonViewModel


class SettingFragment1 : Fragment() {

    lateinit var listReasonAdapter: ListReasonAdapter;
    lateinit var linearLayoutManager: LinearLayoutManager;
    lateinit var listReasonViewModel: ListReasonViewModel;
    lateinit var binding:FragmentSetting1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetting1Binding.inflate(layoutInflater)
        initView();
        binding.btnAdd.setOnClickListener{
            addReason();
        }
        binding.btnGo.setOnClickListener{
            go();
        }
//        binding.edtGetReason.setOnFocusChangeListener { _, hasFocus ->
//            if (hasFocus)
//            else
//               // hideKeyboard()
//        }
        return binding.root;
    }



    private fun initView() {

        listReasonViewModel = ViewModelProvider(this)[ListReasonViewModel::class.java];

        // setup adapter for recycler view
        listReasonAdapter = ListReasonAdapter();
        linearLayoutManager = LinearLayoutManager(activity?.applicationContext!!);
        binding.recyclerView2.layoutManager = linearLayoutManager;

        // view model observe
        listReasonViewModel.getReasonViewModel(activity?.applicationContext!!).observe(viewLifecycleOwner, Observer {
            listReasonAdapter.setData(it);
            binding.recyclerView2.adapter = listReasonAdapter;
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
        binding.btnGo.setOnClickListener{
          Navigation.findNavController(binding.root).navigate(R.id.action_settingFragment1_to_settingFragment2);
        }
    }
}