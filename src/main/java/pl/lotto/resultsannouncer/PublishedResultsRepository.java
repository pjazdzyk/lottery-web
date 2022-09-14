package pl.lotto.resultsannouncer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface PublishedResultsRepository {

    int saveList(List<PublishedResults> lotteryResults);

    Optional<PublishedResults> getPublishedResultsForUuid(UUID uuid);

    Optional<PublishedResults> deletePublishedResultsForUuid(UUID uuid);

    List<PublishedResults> getPublishedResultsForDrawDate(LocalDateTime drawDate);

    List<PublishedResults> deletePublishedResultsForDrawDate(LocalDateTime drawDate);

    List<PublishedResults> getAllPublishedResults();

    boolean contains(PublishedResults lotteryResults);

    List<PublishedResults> getPublishedResultsDrawDateWinnersOnly(LocalDateTime drawDate);

}
