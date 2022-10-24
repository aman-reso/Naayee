package com.naayee.naayeeclient.codes.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.model.ServiceList

class SubCategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    public var subCatItemName : TextView? = itemView.findViewById(R.id.subcatName)

    fun bindDataWithHolder(item: ServiceList){
        subCatItemName?.text = item.ServiceName
    }
}