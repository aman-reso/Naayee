package com.naayee.naayeeclient.codes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.model.EntityModel
import com.naayee.naayeeclient.codes.viewholder.RecommendListViewHolder

class RecommendListAdapter(var callback: (EntityModel?) -> Unit) : RecyclerView.Adapter<RecommendListViewHolder>() {
    private var recommendList = ArrayList<EntityModel>()

    fun submitData(recommendList: ArrayList<EntityModel>) {
        this.recommendList.clear()
        this.recommendList.addAll(recommendList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_shop_each_item, parent, false)
        return RecommendListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendListViewHolder, position: Int) {
        val item = recommendList[position]
        holder.bindDataWithHolder(item)
        holder.itemView.setOnClickListener {
            callback.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return recommendList.size
    }

}