package com.naayee.naayeeclient.codes.interfaces

import com.naayee.naayeeclient.codes.model.ServiceModel

interface ServiceItemClickListener {
    fun minus(serviceModel: ServiceModel)
    fun add(serviceModel:ServiceModel)
}