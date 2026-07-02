package com.mox_flim.me;

import com.google.gson.annotations.SerializedName;

public class M3uSource {
    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    public String getName() { 
        return name != null ? name : "Unknown Channel"; 
    }
    
    public String getUrl() { 
        return url; 
    }
}
