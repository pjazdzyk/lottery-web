package pl.lotto.resultsannouncer;

import pl.lotto.resultschecker.ResultsCheckerFacade;

public class ResultsAnnouncerConfiguration {

    ResultsAnnouncerFacade createForTests(ResultsCheckerFacade resultsCheckerFacade, PublishedResultsCache cacheRepository) {
        return new ResultsAnnouncerFacade(resultsCheckerFacade, cacheRepository);
    }

}
