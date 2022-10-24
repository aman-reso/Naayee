package com.naayee.naayeeclient.codes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.model.ServiceModel
import com.naayee.naayeeclient.codes.viewholder.SelectedServiceViewHolder

class SelectedServiceAdapter : RecyclerView.Adapter<SelectedServiceViewHolder>() {
    private var selectedServiceList: ArrayList<ServiceModel?> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedServiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.selected_service_item_list, parent, false)
        return SelectedServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectedServiceViewHolder, position: Int) {
        holder.bindData(selectedServiceList[position])
    }

    override fun getItemCount(): Int {
        return selectedServiceList.size
    }

    fun submitList(selectedServices: List<ServiceModel?>?) {
        this.selectedServiceList.clear()
        selectedServiceList.addAll(selectedServices!!)
        notifyDataSetChanged()
    }
}