package com.example.gamegiveaway.network

import com.example.gamegiveaway.model.Giveaways
import com.google.android.datatransport.runtime.dagger.Module
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface GiveawaysService {

    @GET(GIVEAWAY_PATH)
    suspend fun getAllGiveaways(
        @Query("sort-by") orderBy: String = SORT_BY_DATE
    ): Response<List<Giveaways>>

    @GET(GIVEAWAY_PATH)
    suspend fun getGiveawaysByPlatform(
        @Query("platform") platform: String
    ): Response<List<Giveaways>>

    //https://www.gamerpower.com/api/giveaways
    companion object{
        const val BASE_URL = "https://www.gamerpower.com/api"
        private const val GIVEAWAY_PATH = "giveaways"
        //sort data by date/value/popularity
        private const val SORT_BY_DATE = "date"
    }
}