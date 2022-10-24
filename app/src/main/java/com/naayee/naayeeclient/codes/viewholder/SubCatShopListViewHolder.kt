package com.naayee.naayeeclient.codes.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.model.EntityModel

class SubCatShopListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    public var subcatShopListShopImage : ImageView? = itemView.findViewById(R.id.shopImage)
    private var subcatShopListshopName : TextView? = itemView.findViewById(R.id.shopName)
    private var subcatShopListaddress : TextView? = itemView.findViewById(R.id.address)
    private var subcatShopListshopTime : TextView? = itemView.findViewById(R.id.shopTime)

    fun bindDataWithHolder(item: EntityModel){
        /* val uri = Uri.parse(item.BannerPath.toString())
         recomendListShopImage?.setImageURI(uri)*/
        subcatShopListshopName?.text = item.EntityName
        subcatShopListaddress?.text = item.Address + ", "+ item.StateName +", "+ item.City +", "+ item.PINCode
        subcatShopListshopTime?.text = item.OpeningTime +" - "+ item.ClosingTime

    }
}