package it.modularity.api.eventbrite.api

import io.reactivex.Observable
import it.modularity.api.eventbrite.response.EventbriteSearchResult
import it.modularity.api.eventbrite.response.Venue
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventbriteApi {

    @GET("/v3/events/search/")
    fun search(
            @Query("token") token: String,
            @Query("q") query: String,
            @Query("location.latitude") latitude: Double,
            @Query("location.longitude") longitude: Double,
            @Query("location.within") distance: String,
            @Query("sort_by") sortBy: String = "distance",
            @Query("price") price: String = "free"
    ): Observable<EventbriteSearchResult>

    @GET("/v3/venues/{id}")
    fun venue(@Path("id") id: String, @Query("token") token: String): Observable<Venue>

}