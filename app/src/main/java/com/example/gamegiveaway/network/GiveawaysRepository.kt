package com.example.gamegiveaway.network

import com.example.gamegiveaway.model.Giveaways
import retrofit2.Response


//repository layer for network calls
//functions from GiveawaysService interface
interface GiveawaysRepository {
    // return query value-parameter
    suspend fun getAllGiveaways(sortedBy: String) :Response<List<Giveaways>>
    suspend fun getGiveawaysByPlatform(sortedBy: String) :Response<List<Giveaways>>
}

//implement suspend functions from giveawayservice interface
class GiveawaysRepositoryImpl (
    private val giveawaysService : GiveawaysService
    ): GiveawaysRepository{

    //
    override suspend fun getAllGiveaways(sortedBy: String): Response<List<Giveaways>> {
        giveawaysService.getAllGiveaways(sortedBy)
    }

    override suspend fun getGiveawaysByPlatform(platform: String): Response<List<Giveaways>> {
        giveawaysService.getGiveawaysByPlatform(platform)
    }
}