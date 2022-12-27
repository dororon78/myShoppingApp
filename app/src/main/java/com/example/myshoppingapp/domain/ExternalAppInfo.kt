package com.example.myshoppingapp.domain

data class ExternalAppInfo(
    val name: String,
    val packageName: String,
    val installable: Boolean,
    val executable: Boolean,
    val marketUri: String
)