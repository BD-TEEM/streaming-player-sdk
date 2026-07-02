package com.mox_flim.me;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/channels.json")
    Call<List<M3uSource>> getStreamingSources();
}
