package com.naayee.naayeeclient.codes.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.model.EntityModel

class RecommendListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    public var recommendListShopImage : ImageView? = itemView.findViewById(R.id.shopImage)
    private var recomendListshopName : TextView? = itemView.findViewById(R.id.shopName)
    private var recomendListaddress : TextView? = itemView.findViewById(R.id.address)
    private var recomendListshopTime : TextView? = itemView.findViewById(R.id.shopTime)

    fun bindDataWithHolder(item: EntityModel){
       /* val uri = Uri.parse(item.BannerPath.toString())
        recomendListShopImage?.setImageURI(uri)*/
        recomendListshopName?.text = item.EntityName
        recomendListaddress?.text = item.Address + ", "+ item.StateName +", "+ item.City +", "+ item.PINCode
        recomendListshopTime?.text = item.OpeningTime +" - "+ item.ClosingTime

    }
}

