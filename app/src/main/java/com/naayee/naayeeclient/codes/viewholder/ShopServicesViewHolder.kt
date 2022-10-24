package com.naayee.naayeeclient.codes.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.model.ServiceModel
import com.naayee.naayeeclient.codes.utility.Utility

class ShopServicesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private var prItemServiceName : TextView? = itemView.findViewById(R.id.prItemServiceName)
    private var prServicePrice : TextView? = itemView.findViewById(R.id.prServicePrice)
    private val countText:TextView = itemView.findViewById(R.id.itemCount)
    val addButton:TextView = itemView.findViewById(R.id.add)
    val minusButton:TextView = itemView.findViewById(R.id.minus)

    fun bindDataWithHolder(item: ServiceModel){
//        println("Service Name --->"+item.ServiceName)
        /* val uri = Uri.parse(item.BannerPath.toString())
         recomendListShopImage?.setImageURI(uri)*/

        prItemServiceName?.text = item.ServiceName
        prServicePrice?.text = "₹ " + item.Price.toString()
        countText.text = "${item.ItemCount}"

    }

}