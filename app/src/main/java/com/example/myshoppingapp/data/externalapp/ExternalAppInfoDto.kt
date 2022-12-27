package com.example.myshoppingapp.data.externalapp

data class ExternalAppInfoDto(
    val name: String,
    val packageName: String,
    val installable: Boolean,
    val executable: Boolean = false,
    val marketUri: String
)
