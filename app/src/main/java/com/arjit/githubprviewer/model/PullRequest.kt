package com.arjit.githubprviewer.model

import com.google.gson.annotations.SerializedName

data class PullRequest(
    private val id: String,
    val number: Int,
    val title: String,
    val user: User,
    val body: String,
    @SerializedName("created_at") val createdAt: String
) : DiffModel(id) {

    // done only to remove CLA signed tag.
    val labels: List<Label> = listOf()
    get() {
        if (field.isEmpty())
            return field
        return field.takeLast(field.size - 1)
    }
}
