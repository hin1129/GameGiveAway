package com.example.gamegiveaway.di

import android.content.Context
import androidx.room.Room
import com.example.gamegiveaway.database.DatabaseRepository
import com.example.gamegiveaway.database.DatabaseRepositoryImpl
import com.example.gamegiveaway.database.GiveawaysDAO
import com.example.gamegiveaway.database.GiveawaysDatabase
import com.example.gamegiveaway.network.GiveawaysRepository
import com.example.gamegiveaway.network.GiveawaysRepositoryImpl
import com.example.gamegiveaway.network.GiveawaysService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


//provide internet connection
val networkModule = module{

    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build()

    fun providesNetworkService(okHttpClient: OkHttpClient): GiveawaysService =
        Retrofit.Builder()
            .baseUrl(GiveawaysService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GiveawaysService::class.java)


    fun providesGiveawaysRepository(networkService: GiveawaysService) :GiveawaysRepository =
        //class name(parameter)
        GiveawaysRepositoryImpl(networkService)


//    fun providesDatabaseRepository(networkService: GiveawaysService) :DatabaseRepository =
//        DatabaseRepositoryImpl(networkService)

    //pass functions, get that specific object
    single { providesLoggingInterceptor() }
    single { providesNetworkService(get()) }
    single { providesOkHttpClient(get()) }
    single { providesGiveawaysRepository(get()) }
}


// provides database objects
val applicationModule = module {

    fun providesGiveawaysDatabase(context: Context) :GiveawaysDatabase
    //initialise DB = context, abstract class, name of DB
    = Room.databaseBuilder(context, GiveawaysDatabase::class.java, "giveaway DB")
        .build()



    fun providesGiveawaysDoa(giveawaysDatabase: GiveawaysDatabase) :GiveawaysDAO =
        giveawaysDatabase.getGiveawaysDao()

    fun providesDatabaseRepository(databaseDAO: GiveawaysDAO) :GiveawaysRepository =
        //class name(parameter)
        DatabaseRepositoryImpl(databaseDAO)

    single { providesGiveawaysDatabase(androidContext()) }
    single { providesGiveawaysDoa(get()) }
    single { providesDatabaseRepository(get()) }
}