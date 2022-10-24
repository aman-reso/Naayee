package com.naayee.naayeeclient.codes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.interfaces.ServiceItemClickListener
import com.naayee.naayeeclient.codes.model.ServiceModel
import com.naayee.naayeeclient.codes.viewholder.ShopServicesViewHolder
import okhttp3.internal.filterList

class ShopServicesAdapter() : RecyclerView.Adapter<ShopServicesViewHolder>() {
    private var shopServiceList = ArrayList<ServiceModel>()

    fun submitData(shopServiceList: ArrayList<ServiceModel>) {
        this.shopServiceList.clear()
        this.shopServiceList.addAll(shopServiceList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopServicesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.prserviceitem, parent, false)
        return ShopServicesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopServicesViewHolder, position: Int) {
        holder.bindDataWithHolder(shopServiceList[position])

        holder.addButton.setOnClickListener {
            var count = shopServiceList[position].ItemCount
            count += 1
            shopServiceList[position].ItemCount = count
            notifyItemChanged(position)
        }

        holder.minusButton.setOnClickListener {
            var count = shopServiceList[position].ItemCount
            if (count > 0) {
                count -= 1
                shopServiceList[position].ItemCount = count
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return shopServiceList.size
    }

     fun getSelectedServiceList(): List<ServiceModel> {
        return shopServiceList.filterList {this.ItemCount>0}
    }
}