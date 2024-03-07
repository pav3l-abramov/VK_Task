package com.example.vktask.data

data class Products(
    val products: List<Product>
)

data class Product(
    val id :Int?=null,
    val title :String?=null,
    val description :String?=null,
    val price :Int?=null,
    val discountPercentage :Float?=null,
    val rating :Float?=null,
    val stock :Int?=null,
    val brand :String?=null,
    val category :String?=null,
    val thumbnail :String?=null,
    val images :List<String>?=null
)
