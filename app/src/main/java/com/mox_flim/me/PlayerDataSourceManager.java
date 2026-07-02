package com.mox_flim.me;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.datasource.cache.CacheDataSource;
import androidx.media3.datasource.cache.SimpleCache;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the initialization of cache and HTTP data sources for ExoPlayer.
 * Provides a caching data source layer to save bandwidth and improve playback startup times.
 */
@UnstableApi // Required for cache APIs in Media3
public class PlayerDataSourceManager {

    private final Context context;
    private final Map<String, String> customHttpHeaders = new HashMap<>();

    public PlayerDataSourceManager(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Appends a custom HTTP header (like an Authorization token or User-Agent)
     * to all downstream network requests.
     */
    public void addHttpHeader(@NonNull String key, @NonNull String value) {
        customHttpHeaders.put(key, value);
    }

    /**
     * Builds a {@link DataSource.Factory} configured with:
     * - Network data source (supporting customized HTTP headers)
     * - Disk caching data source
     */
    @NonNull
    public DataSource.Factory buildCacheDataSourceFactory() {
        // Create HTTP connection factory
        DefaultHttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory()
                .setAllowCrossProtocolRedirects(true)
                .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
                .setReadTimeoutMs(DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS);

        if (!customHttpHeaders.isEmpty()) {
            httpDataSourceFactory.setDefaultRequestProperties(customHttpHeaders);
        }

        // Wrap HTTP connection factory with shared player cache
        SimpleCache cache = PlayerCacheManager.getInstance(context).getCache();
        if (cache != null) {
            return new CacheDataSource.Factory()
                    .setCache(cache)
                    .setUpstreamDataSourceFactory(httpDataSourceFactory)
                    .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
        }

        return httpDataSourceFactory;
    }
}

