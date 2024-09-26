package com.vnazarov.clerktest.di

import android.content.Context
import androidx.room.Room
import com.vnazarov.data.local.database.CardsDatabase
import com.vnazarov.data.remote.CardsApiService
import com.vnazarov.data.repository.LocalCardsRepositoryImpl
import com.vnazarov.data.repository.RemoteCardsRepositoryImpl
import com.vnazarov.domain.repository.local.LocalCardsRepository
import com.vnazarov.domain.repository.remote.RemoteCardsRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {

    //Api
    single { buildRetrofit().create(CardsApiService::class.java) }

    single<RemoteCardsRepository> { RemoteCardsRepositoryImpl(get()) }

    //DB
    single { buildDatabase(get()) }

    single { get<CardsDatabase>().cardsDao() }

    single<LocalCardsRepository> { LocalCardsRepositoryImpl(get()) }
}

private fun buildDatabase(context: Context) =
    Room.databaseBuilder(
        context,
        CardsDatabase::class.java,
        "favourite_cards"
    ).fallbackToDestructiveMigration().build()

private fun buildRetrofit() =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildClient())
        .build()

private fun buildClient() =
    OkHttpClient.Builder()
        .callTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader(KEY_HEADER, KEY)
                .addHeader(HOST_HEADER, HOST)
                .build()

            chain.proceed(newRequest)
        }
        .build()

private const val TIME_OUT = 240L

private const val BASE_URL = "https://omgvamp-hearthstone-v1.p.rapidapi.com"

private const val KEY_HEADER = "x-rapidapi-key"
private const val KEY = "c89db9f582msh830591d81005b7ap1dad60jsnb07f1ec5d0a7"

private const val HOST_HEADER = "x-rapidapi-host"
private const val HOST = "omgvamp-hearthstone-v1.p.rapidapi.com"