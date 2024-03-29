package com.cscorner.diabetique.apis

import com.cscorner.diabetique.response.SearchRecipes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIClient {
    @GET("/api/recipes/v2")
    fun searchRecipes(
        @Query("type") type: String,
        @Query("q") query: String,
        @Query("app_id") idApp: String,
        @Query("app_key") keyApp: String
    ): Call<SearchRecipes>
}
