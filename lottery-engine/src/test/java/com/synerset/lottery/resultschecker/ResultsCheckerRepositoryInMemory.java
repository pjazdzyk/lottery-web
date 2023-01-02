package com.synerset.lottery.resultschecker;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

class ResultsCheckerRepositoryInMemory implements ResultsCheckerRepository {

    private final Map<UUID, LotteryResults> databaseInMemory = new ConcurrentHashMap<>();

    @Override
    public List<LotteryResults> findByDrawDate(LocalDateTime drawDate) {
        return databaseInMemory.values()
                .stream()
                .filter(lotteryResult -> drawDate.isEqual(lotteryResult.drawDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<LotteryResults> findByDrawDateAndIsWinner(LocalDateTime drawDate, boolean isWinner) {
        return databaseInMemory.values()
                .stream()
                .filter(lotteryResult -> drawDate.isEqual(lotteryResult.drawDate()))
                .filter(lotteryResults -> lotteryResults.isWinner() == isWinner)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByDrawDate(LocalDateTime drawDate) {
        return false;
    }

    @Override
    public <S extends LotteryResults> S save(S entity) {
        databaseInMemory.put(entity.uuid(), entity);
        return entity;
    }

    @Override
    public <S extends LotteryResults> List<S> saveAll(Iterable<S> entities) {
        List<S> tempList = new ArrayList<>();
        Iterator<S> iterator = entities.iterator();
        S entity;
        while (iterator.hasNext()) {
            entity = iterator.next();
            save(entity);
            tempList.add(entity);
        }
        return tempList;
    }

    @Override
    public Optional<LotteryResults> findById(UUID uuid) {
        return Optional.ofNullable(databaseInMemory.get(uuid));
    }

    @Override
    public boolean existsById(UUID uuid) {
        return databaseInMemory.containsKey(uuid);
    }

    @Override
    public List<LotteryResults> findAll() {
        return new ArrayList<>(databaseInMemory.values());
    }

    @Override
    public List<LotteryResults> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {
        databaseInMemory.remove(uuid);
    }

    @Override
    public void delete(LotteryResults entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends LotteryResults> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<LotteryResults> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<LotteryResults> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends LotteryResults> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends LotteryResults> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends LotteryResults> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends LotteryResults> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends LotteryResults> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends LotteryResults> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends LotteryResults> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends LotteryResults> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends LotteryResults, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
