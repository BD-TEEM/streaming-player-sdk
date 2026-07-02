package com.mox_flim.me;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.database.StandaloneDatabaseProvider;
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor;
import androidx.media3.datasource.cache.SimpleCache;
import java.io.File;

@UnstableApi
public class PlayerCacheManager {

    private static PlayerCacheManager instance;
    private SimpleCache cache;

    private PlayerCacheManager(@NonNull Context context) {
        File cacheDir = new File(context.getCacheDir(), "media3_video_cache");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        // 100 MB max cache size
        LeastRecentlyUsedCacheEvictor evictor = new LeastRecentlyUsedCacheEvictor(100 * 1024 * 1024);
        StandaloneDatabaseProvider databaseProvider = new StandaloneDatabaseProvider(context);
        cache = new SimpleCache(cacheDir, evictor, databaseProvider);
    }

    public static synchronized PlayerCacheManager getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new PlayerCacheManager(context);
        }
        return instance;
    }

    public SimpleCache getCache() {
        return cache;
    }
}

