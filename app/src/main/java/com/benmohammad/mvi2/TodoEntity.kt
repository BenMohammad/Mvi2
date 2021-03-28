package com.benmohammad.mvi2

import java.util.*

data class TodoEntity(
    val  title: String,
    val description: String,
    val completed: Boolean = false,
    val id: String = UUID.randomUUID().toString()
)
