package com.naayee.naayeeclient.codes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.model.ServiceList
import com.naayee.naayeeclient.codes.viewholder.SubCategoryViewHolder

class SubCategoryListAdapter : RecyclerView.Adapter<SubCategoryViewHolder>() {
    private var subCatList = ArrayList<ServiceList>()

    fun submitData(subCatList: ArrayList<ServiceList>) {
        this.subCatList.clear()
        this.subCatList.addAll(subCatList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subcategory_child_list, parent, false)
        return SubCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        val item = subCatList[position]
        holder.bindDataWithHolder(item)
        holder.itemView?.setOnClickListener{

        }
        /* holder.sendToWapIcon?.setOnClickListener {
             callback(item)
         }*/
    }

    override fun getItemCount(): Int {
        return subCatList.size
    }
}