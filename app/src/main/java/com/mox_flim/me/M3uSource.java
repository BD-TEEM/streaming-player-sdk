package com.mox_flim.me;

import com.google.gson.annotations.SerializedName;

public class M3uSource {
    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url; // এখানে .m3u8 বা .mp4 লিংক থাকবে

    public String getName() { return name; }
    public String getUrl() { return url; }
}
