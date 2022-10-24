package com.naayee.naayeeclient.codes.model

data class PaymentCnfReqModel(
    var ErrorCode: String?="",
    var ErrorMsg: String?="",
    var OrderId: String?="",
    var Rz_order_id: String?="",
    var Rz_payment_id: String?="",
    var Rz_signature: String?="",
    var Status: Int?=0,
    var TxnNo: String?=""
)