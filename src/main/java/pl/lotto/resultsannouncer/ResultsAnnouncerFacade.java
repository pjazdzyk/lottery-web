package pl.lotto.resultsannouncer;

import pl.lotto.resultsannouncer.dto.PublishedResultsDto;
import pl.lotto.resultschecker.ResultsCheckerFacade;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ResultsAnnouncerFacade {

    ResultsCheckerFacade resultsCheckerFacade;
    PublishedResultsRepository publishedResultsRepository;
    PublishedResultsRepositorySynchronizer resultsRepositorySynchronizer;

    public ResultsAnnouncerFacade(ResultsCheckerFacade resultsCheckerFacade, PublishedResultsRepository publishedResultsRepository,
                                  PublishedResultsRepositorySynchronizer resultsRepositorySynchronizer) {

        this.resultsCheckerFacade = resultsCheckerFacade;
        this.publishedResultsRepository = publishedResultsRepository;
        this.resultsRepositorySynchronizer = resultsRepositorySynchronizer;
    }

    public int synchronizeRepository(){
        return resultsRepositorySynchronizer.synchronizeRepository();
    }

    public Optional<PublishedResultsDto> getResultsForId(UUID uuid) {
        Optional<PublishedResults> publishedResultsOptional = publishedResultsRepository.getPublishedResultsForUuid(uuid);
        return publishedResultsOptional.map(PublishedResultsMapper::toDto);
    }

    public Optional<PublishedResultsDto> deletePublishedResultsForUuid(UUID uuid) {
        Optional<PublishedResults> publishedResultsOptional = publishedResultsRepository.deletePublishedResultsForUuid(uuid);
        return publishedResultsOptional.map(PublishedResultsMapper::toDto);
    }

    public List<PublishedResultsDto> getPublishedResultsForDrawDate(LocalDateTime drawDate) {
        List<PublishedResults> publishedResults = publishedResultsRepository.getPublishedResultsForDrawDate(drawDate);
        return PublishedResultsMapper.toDtoList(publishedResults);
    }

    public List<PublishedResultsDto> getPublishedResultsDrawDateWinnersOnly(LocalDateTime drawDate){
        List<PublishedResults> publishedResults = publishedResultsRepository.getPublishedResultsDrawDateWinnersOnly(drawDate);
        return PublishedResultsMapper.toDtoList(publishedResults);
    }

    public List<PublishedResultsDto> deletePublishedResultsForDrawDate(LocalDateTime drawDate) {
        List<PublishedResults> publishedResults = publishedResultsRepository.deletePublishedResultsForDrawDate(drawDate);
        return PublishedResultsMapper.toDtoList(publishedResults);
    }

    public List<PublishedResultsDto> getAllPublishedResults() {
        List<PublishedResults> publishedResults = publishedResultsRepository.getAllPublishedResults();
        return PublishedResultsMapper.toDtoList(publishedResults);
    }


}
