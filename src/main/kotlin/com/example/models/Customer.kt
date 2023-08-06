package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Customer(val id: String, val firstName: String, val lastName: String, val email: String)

//TODO, this is in memory , but in real world we will save it in persistence for example: using exposed
val customerStorage = mutableListOf<Customer>()