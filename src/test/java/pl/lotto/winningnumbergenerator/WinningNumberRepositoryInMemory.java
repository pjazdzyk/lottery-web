package pl.lotto.winningnumbergenerator;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class WinningNumberRepositoryInMemory implements WinningNumberRepository {

    private final Map<LocalDateTime, WinningNumbers> databaseInMemory = new ConcurrentHashMap<>();

    public Optional<WinningNumbers> findByDrawDate(LocalDateTime drawDate){
        return Optional.ofNullable(databaseInMemory.get(drawDate));
    }
    public void deleteByDrawDate(LocalDateTime drawDate){
        databaseInMemory.remove(drawDate);
    }
   public boolean existsByDrawDate(LocalDateTime drawDate){
        return databaseInMemory.containsKey(drawDate);
    }

    @Override
    public <S extends WinningNumbers> S save(S entity) {
        if (!databaseInMemory.containsKey(entity.drawDate())) {
            databaseInMemory.put(entity.drawDate(), entity);
        }
        return entity;
    }

    @Override
    public <S extends WinningNumbers> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<WinningNumbers> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public List<WinningNumbers> findAll() {
        return new ArrayList<>(databaseInMemory.values());
    }

    @Override
    public Iterable<WinningNumbers> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void delete(WinningNumbers entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends WinningNumbers> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<WinningNumbers> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<WinningNumbers> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends WinningNumbers> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends WinningNumbers> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends WinningNumbers> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends WinningNumbers> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends WinningNumbers> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends WinningNumbers> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends WinningNumbers> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends WinningNumbers> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends WinningNumbers, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
