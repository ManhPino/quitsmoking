package com.example.quitsmokingnow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.example.quitsmokingnow.databinding.ActivityDefaultBinding

class DefaultActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDefaultBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityDefaultBinding.inflate(layoutInflater);
        setContentView(binding.root)
      //  Navigation.findNavController(binding.root).navigate(R.id.action_defaultIntroduceFragment_to_settingFragment1)
    }
}