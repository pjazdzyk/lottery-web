package pl.lottery.numberreceiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
interface CouponRepository extends MongoRepository<Coupon, UUID> {

    List<Coupon> findByDrawDate(LocalDateTime drawDate);

}

