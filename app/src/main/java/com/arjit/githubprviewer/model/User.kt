package com.arjit.githubprviewer.model

import com.google.gson.annotations.SerializedName

data class User(
    val login: String,
    private val id: Int,
    @SerializedName("avatar_url") val avatarUrl: String
)
