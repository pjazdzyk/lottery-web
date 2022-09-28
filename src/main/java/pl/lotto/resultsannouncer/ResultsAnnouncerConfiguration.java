package pl.lotto.resultsannouncer;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import pl.lotto.resultschecker.ResultsCheckerFacade;

import java.time.Duration;


public class ResultsAnnouncerConfiguration {

    ResultsAnnouncerFacade createForTests(ResultsCheckerFacade resultsCheckerFacade, PublishedResultsCache cacheRepository) {
        return crateForProduction(resultsCheckerFacade, cacheRepository);
    }

    ResultsAnnouncerFacade crateForProduction(ResultsCheckerFacade resultsCheckerFacade, PublishedResultsCache cacheRepository) {
        return new ResultsAnnouncerFacade(resultsCheckerFacade, cacheRepository);
    }


    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

}
