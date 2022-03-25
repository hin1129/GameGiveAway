package com.example.gamegiveaway.database

import androidx.room.Delete
import androidx.room.Query
import com.example.gamegiveaway.model.Giveaways


//repository layer for network calls
// suspend functions from interface GiveawaysDAO
interface DatabaseRepository {
    suspend fun insertGiveaways(newGiveaways: List<Giveaways>)
    suspend fun getAllGiveaways(): List<Giveaways>
    suspend fun getGiveawayById(searchId:Int): Giveaways
    suspend fun getGiveawaysByPlatform(platform: String): List<Giveaways>
    suspend fun deleteGiveaways( giveaways: List<Giveaways>)

}

// implement above interface
//implement suspend functions from giveaway database class
class DatabaseRepositoryImpl (
    // variable for GiveawaysDAO interface from GiveawaysDatabase class
    private val giveawaysDatabase: GiveawaysDAO
        ): DatabaseRepository {

    override suspend fun insertGiveaways(newGiveaways: List<Giveaways>) {
        //use variable to call suspend function
        giveawaysDatabase.insertGiveaways(newGiveaways)
    }

    override suspend fun getAllGiveaways(): List<Giveaways> {
        return giveawaysDatabase.getAllGiveaways()
    }

    override suspend fun getGiveawayById(searchId: Int): Giveaways {
        return giveawaysDatabase.getGiveawayById(searchId)
    }

    override suspend fun getGiveawaysByPlatform(platform: String): List<Giveaways> {
        return giveawaysDatabase.getGiveawaysByPlatform(platform)
    }

    override suspend fun deleteGiveaways(giveaways: List<Giveaways>) {
        //delete first element
        return giveawaysDatabase.deleteGiveaways(giveaways)
    }


}