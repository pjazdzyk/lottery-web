package pl.lotto.resultschecker;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository()
interface ResultsCheckerRepository extends MongoRepository<LotteryResults, UUID> {

    List<LotteryResults> findByDrawDate(LocalDateTime drawDate);
    List<LotteryResults> findByDrawDateAndIsWinner(LocalDateTime drawDate, boolean isWinner);

}



