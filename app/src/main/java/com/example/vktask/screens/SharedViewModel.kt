package com.example.vktask.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.vktask.data.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val _dataProduct : MutableState<Product> = mutableStateOf(Product(0,"","",0, thumbnail = ""))
    val dataProduct: State<Product> = _dataProduct

    fun setData (data: Product){
        _dataProduct.value=data
    }
}