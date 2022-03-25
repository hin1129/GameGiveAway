package com.example.gamegiveaway.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamegiveaway.model.Giveaways
import com.example.gamegiveaway.network.GiveawaysRepository
import com.example.gamegiveaway.utils.GiveawayState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.coroutineContext

class GiveawayViewModel(

    private val networkRepo: GiveawaysRepository,
    private val databaseRepo: GiveawaysRepository,
    //MAIN=main thread; IO=long running operations, default for network/database call
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
//    private val coroutineScope: CoroutineScope = CoroutineScope()
) : ViewModel() {

    init { Log.d("giveawysviewmodel", ":viewmodel destroy ") }

    //require live data to update UI
    //<seal class requires state>, giveawaystate as data type that holds by live data
    private val _sortedGiveaways : MutableLiveData<GiveawayState> = MutableLiveData(GiveawayState.LOADING)
    //to be observed in fragment/activity
    val giveaways: LiveData<GiveawayState> get() = _sortedGiveaways

    //pass network calls
    fun getSortedGiveaways(sortBy:String = "date"){
        //switch to worker thread
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = networkRepo.getAllGiveaways(sortBy)

                //gets for response
                if (response.isSuccessful){
                    response.body()?.let {

                        // if body is not null
                        //call DB
                        databaseRepo.insertGiveaways(it)
                        val localData = databaseRepo.getAllGiveaways()

                        //post value to live data
                        _sortedGiveaways.postValue(GiveawayState.SUCCESS(localData))
                    }
                        //if body is null
                        ?: throw Exception("response in null")
                }
                else{ throw Exception("no successful response") }
            }
            //catch error (none of above), from network call
            catch (e: Exception){
                _sortedGiveaways.postValue(GiveawayState.ERROR(e))
                //pull from offline mode DB
                loadFromDB()
            }
        }
    }

    //offline mode of app
    private suspend fun loadFromDB(){
        try{
            val localData = databaseRepo.getAllGiveaways()
            _sortedGiveaways.postValue(GiveawayState.SUCCESS(localData, isLocalData = true))
        }
        //catch error (none of above), from DB
        catch (e: Exception){
            _sortedGiveaways.postValue(GiveawayState.ERROR(e))
        }
    }

    //pass network calls
    fun getGiveawaysByPlatform(platform :String = "pc"){
        viewModelScope.launch (ioDispatcher) {
            try {
                val response = networkRepo.getGiveawaysByPlatform(platform)

                if (response.isSuccessful){
                    response.body()?.let {

                        // if body is not null
                        _sortedGiveaways.postValue(GiveawayState.SUCCESS(it))
                    }
                        // if body is null
                        ?: throw Exception("response in null")
                }
                else{
                    throw Exception("no successful response")
                }
            }
            //catch error (none of above)
            catch (e: Exception){
                _sortedGiveaways.postValue(GiveawayState.ERROR(e))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("giveawayviewmodel", "viewmodel destroy")
    }
}