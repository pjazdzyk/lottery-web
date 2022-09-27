package pl.lotto.winningnumbergenerator;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
interface WinningNumberRepository extends MongoRepository<WinningNumbers, UUID> {

    Optional<WinningNumbers> findByDrawDate(LocalDateTime drawDate);
    void deleteByDrawDate(LocalDateTime drawDate);
    boolean existsByDrawDate(LocalDateTime drawDate);

}
