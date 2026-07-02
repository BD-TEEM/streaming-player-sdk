package com.mox_flim.me;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    // আপনার নিজস্ব m3u/m3u8 এপিআই লিংক (উদাহরণস্বরূপ: "playlist.json")
    @GET("path/to/your/m3u/api.json")
    Call<List<M3uSource>> getStreamingSources();
}

