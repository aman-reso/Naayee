package com.naayee.naayeeclient.codes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.model.EntityModel
import com.naayee.naayeeclient.codes.viewholder.SubCatShopListViewHolder

class SubCatShopListAdapter: RecyclerView.Adapter<SubCatShopListViewHolder>(){
    private var subCatShopList = ArrayList<EntityModel>()

    fun submitData(subshopList: ArrayList<EntityModel>) {
        this.subCatShopList.clear()
        this.subCatShopList.addAll(subshopList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCatShopListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_shop_each_item, parent, false)
        return SubCatShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubCatShopListViewHolder, position: Int) {
        val item = subCatShopList[position]
        holder.bindDataWithHolder(item)
        holder.itemView?.setOnClickListener{

        }
        /* holder.sendToWapIcon?.setOnClickListener {
             callback(item)
         }*/
    }

    override fun getItemCount(): Int {
        return subCatShopList.size
    }

}