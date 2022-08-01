package com.example.quitsmokingnow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quitsmokingnow.databinding.CustomListreasonBinding
import com.example.quitsmokingnow.model.ListReasonQuitSmoking

class ListReasonAdapter: RecyclerView.Adapter<ListReasonAdapter.ListReasonViewHolder>() {
    var list:List<ListReasonQuitSmoking> = emptyList();
    fun setData(list:List<ListReasonQuitSmoking>){
        this.list = list;
        notifyDataSetChanged();
    }
    inner class ListReasonViewHolder(val binding: CustomListreasonBinding) : RecyclerView.ViewHolder(binding.root) {
       fun bind(reason:ListReasonQuitSmoking){
           binding.name.text = " ${reason.mReason}"
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListReasonViewHolder {
        return ListReasonViewHolder(CustomListreasonBinding.inflate(LayoutInflater.from(parent.context),parent,false));
    }

    override fun onBindViewHolder(holder: ListReasonViewHolder, position: Int) {
        val reason = list[position];
        holder.bind(reason)
    }

    override fun getItemCount(): Int {
      return list.size;
    }
}