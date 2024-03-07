package com.example.vktask.retrofit

import com.example.vktask.data.CategoriesData
import com.example.vktask.data.Products


sealed class GetStateList {
    object Loading : GetStateList()
    data class Success(val products: Products,val onSearch:Boolean) : GetStateList()
    data class Error(val error: String) : GetStateList()
}
sealed class GetStateCategories {
    object Loading : GetStateCategories()
    data class Success(val categories: CategoriesData ) : GetStateCategories()
    data class Error(val error: String) : GetStateCategories()
}