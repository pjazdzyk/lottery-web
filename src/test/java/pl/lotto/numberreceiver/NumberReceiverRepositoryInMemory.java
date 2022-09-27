package pl.lotto.numberreceiver;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

class NumberReceiverRepositoryInMemory implements CouponRepository {

    private final Map<UUID, Coupon> databaseInMemory = new ConcurrentHashMap<>();

    // CUSTOM

    @Override
    public List<Coupon> findByDrawDate(LocalDateTime drawDate) {
        return databaseInMemory.values()
                .stream()
                .filter(coupon -> drawDate.isEqual(coupon.drawDate()))
                .collect(Collectors.toList());
    }

    // REPOSITORY IMPLEMENTED

    @Override
    public <S extends Coupon> S save(S entity) {
        databaseInMemory.put(entity.uuid(), entity);
        return entity;
    }

    @Override
    public Optional<Coupon> findById(UUID uuid) {
        return Optional.ofNullable(databaseInMemory.get(uuid));
    }

    @Override
    public void deleteById(UUID uuid) {
        databaseInMemory.remove(uuid);
    }

    @Override
    public List<Coupon> findAll() {
        return new ArrayList<>(databaseInMemory.values());
    }

    @Override
    public List<Coupon> findAll(Sort sort) {
        return null;
    }

    @Override
    public boolean existsById(UUID uuid) {
        return databaseInMemory.containsKey(uuid);
    }


    // NOT IMPLEMENTED

    @Override
    public <S extends Coupon> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Iterable<Coupon> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Coupon entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Coupon> entities) {

    }

    @Override
    public void deleteAll() {

    }



    @Override
    public Page<Coupon> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Coupon> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Coupon> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Coupon> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Coupon> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Coupon> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Coupon> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Coupon> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Coupon> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Coupon, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
