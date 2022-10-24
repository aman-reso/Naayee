package com.naayee.naayeeclient.codes.model

data class PaymentInitResponse(
    val EmailId: String?,
    val EntityId: Int?=-1,
    val Message: String?=null,
    val MobileNo: String?=null,
    val Name: String?="",
    val OrderId: String?=null,
    val Status: Int?=-1,
    val TotalAmt: Double?=0.0,
    val TotalDiscount: Double?=0.0,
    val TotalDiscountP: Double?=0.0,
    val TotalGST: Double?=0.0,
    val TotalGSTP: Double?=0.0,
    val TxnNo: String?=null,
    var Rz_order_id:String?=null,
    var Rz_Receipt:String?=null
)