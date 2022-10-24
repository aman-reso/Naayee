package com.naayee.naayeeclient.codes.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.model.ServiceCategory



class CategoryAdapter(private val context : Activity, private val arrayList : ArrayList<ServiceCategory>) : ArrayAdapter<ServiceCategory>(context,
    R.layout.category_child_layout,arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater :LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.category_child_layout,null)

        val childImageView: ImageView = view.findViewById(R.id.childGridIcon)
        val childcatLbl : TextView = view.findViewById(R.id.childGridLabel)

        childImageView.setImageResource(arrayList[position].catImage)
        childcatLbl.text = arrayList[position].catName

        return view
    }
}