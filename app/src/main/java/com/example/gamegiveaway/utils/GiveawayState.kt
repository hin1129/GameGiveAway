package com.example.gamegiveaway.utils



//provide UI a state for different states
//for object/data classes
// only way to access this class = use below 3 methods
sealed class GiveawayState {

    //provide UI loading state
    object LOADING : GiveawayState()

    //provide UI success state, inflate recycle view
    class SUCCESS <T> (val giveaways:T, isLocalData: Boolean = false) : GiveawayState()

    //provide UI error state, provide dialog
    class ERROR (val error: Throwable) : GiveawayState()
}