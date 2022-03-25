package com.example.gamegiveaway.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.datatransport.runtime.dagger.Module
import com.google.gson.annotations.SerializedName


@Entity
data class Giveaways(
    @SerializedName("description")
    val description: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("gamerpower_url")
    val gamerpowerUrl: String,

    @SerializedName("id")
    @PrimaryKey
    val id: Int,

    @SerializedName("image")
    val image: String,
    @SerializedName("instructions")
    val instructions: String,
    @SerializedName("open_giveaway")
    val openGiveaway: String,
    @SerializedName("open_giveaway_url")
    val openGiveawayUrl: String,

    @SerializedName("platforms")
    val platforms: String,

    @SerializedName("published_date")
    val publishedDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("users")
    val users: Int,
    @SerializedName("worth")
    val worth: String
)