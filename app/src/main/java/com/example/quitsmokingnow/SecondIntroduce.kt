package com.example.quitsmokingnow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quitsmokingnow.databinding.ActivitySecondIntroduceBinding

class SecondIntroduce : AppCompatActivity() {
    lateinit var binding:ActivitySecondIntroduceBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondIntroduceBinding.inflate(layoutInflater);
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
           back()
        }
        binding.btnGo.setOnClickListener{
            go()
        }
    }
    private fun go(){
        var intent = Intent(this,MainActivity::class.java);
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
        finish();
    }
    private fun back(){
        var intent = Intent(this,FirstIntroduce::class.java);
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        finish();
    }
}