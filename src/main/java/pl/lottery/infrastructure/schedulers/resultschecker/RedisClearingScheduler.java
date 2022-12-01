package pl.lottery.infrastructure.schedulers.resultschecker;

import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
class RedisClearingScheduler {
    private final CacheManager cacheManager;

    public RedisClearingScheduler(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Scheduled(cron = "${lotto.checker.lotteryRunOccurrence}")
    void clearRedisCache() {
        cacheManager.getCacheNames().forEach(cache -> cacheManager.getCache(cache).clear());
    }

}
