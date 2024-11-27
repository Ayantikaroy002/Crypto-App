package com.example.myapplication

import java.io.Serializable

data class Modal(
    val name: String,
    val symbol: String,
    val price: String
) : Serializable
