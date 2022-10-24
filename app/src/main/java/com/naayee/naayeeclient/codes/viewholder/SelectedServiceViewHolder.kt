package com.naayee.naayeeclient.codes.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.model.ServiceModel

class SelectedServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val serviceName: TextView? by lazy { itemView.findViewById(R.id.serviceNameTextView) }
    private val serviceCount: TextView? by lazy { itemView.findViewById(R.id.serviceCountTextView) }

    fun bindData(serviceModel: ServiceModel?) {
        if (serviceModel != null) {
            serviceName?.text = serviceModel.ServiceName
            serviceCount?.text = serviceModel.ItemCount.toString()
        }
    }

}