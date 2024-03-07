package com.example.vktask.retrofit

import com.example.vktask.data.Products


sealed class GetStateList {
    object Loading : GetStateList()
    data class Success(val products: Products,val onSearch:Boolean) : GetStateList()
    data class Error(val error: String) : GetStateList()
}
//sealed class GetStateItem {
//    object Loading : GetStateItem()
//    data class Success(val materialDetails: MaterialDetails ) : GetStateItem()
//    data class Error(val error: String) : GetStateItem()
//}