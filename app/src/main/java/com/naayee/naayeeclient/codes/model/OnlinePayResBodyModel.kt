package com.naayee.naayeeclient.codes.model

data class OnlinePayResBodyModel(var Id: Int,
                                 var UserId: String,
                                 var EntityId: String,
                                 var RequestAmount: Int,
                                 var OrderId: String,
                                 var TxnNo: String,
                                 var Status: String,
                                 var ErrorCode: String,
                                 var ErrorMsg: String)