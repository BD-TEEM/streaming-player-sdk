package com.mox_flim.me;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    
    // মুভির তালিকা নিয়ে আসার এন্ডপয়েন্ট
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
        @Query("api_key") String apiKey,
        @Query("page") int page
    );
}

