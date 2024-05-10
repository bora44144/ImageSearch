package com.example.imagesearch

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ImageService {
    @Headers("Authorization: KakaoAK fb3efca8528cae5264b6d34444d4317b")
    @GET("v2/search/image")
    fun searchImage(
        @Query("query") query: String
    ): Call<Image>
}