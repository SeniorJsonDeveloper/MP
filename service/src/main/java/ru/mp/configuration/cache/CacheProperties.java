package ru.mp.configuration.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
@ConfigurationProperties(prefix = "app.cache")
@Component
public class CacheProperties {

    private final List<String> cacheNames = new ArrayList<>();

    private final Map<String,AppCacheProperties> caches = new HashMap<>();

    private CacheType cacheType;

    @Data
    public static class AppCacheProperties{
        private Duration expiry = Duration.ZERO;
    }

    public interface CacheNames {
        String ITEM_BY_ID = "itemById";
        String ITEM_BY_FILTER = "itemFilter";
        String ITEMS = "items";
        String NEW_ITEM = "newItem";
        String DEAL = "deal";
        String DEAL_BY_ID = "dealById";
        String SHOP_BY_ID = "shopById";
        String SHOP_BY_FILTER = "shopFilter";
        String SHOPS = "shops";
        String ITEM_BY_NAME = "itemByName";

    }
    public enum CacheType{
        IN_MEMORY,REDIS
    }
}
