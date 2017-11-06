package it.modularity.api.eventbrite.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class EventbriteService(endpoint: String = ENDPOINT) {

    companion object {
        private val ENDPOINT = "https://www.eventbriteapi.com/"
    }

    val api: EventbriteApi = Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient())
            .build().create(EventbriteApi::class.java)


    private fun httpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

}