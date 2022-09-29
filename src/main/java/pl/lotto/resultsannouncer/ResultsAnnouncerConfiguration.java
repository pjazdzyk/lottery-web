package pl.lotto.resultsannouncer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.resultschecker.ResultsCheckerFacade;

@Configuration
public class ResultsAnnouncerConfiguration {

    public ResultsAnnouncerFacade createForTests(ResultsCheckerFacade resultsCheckerFacade) {
        return crateForProduction(resultsCheckerFacade);
    }

    @Bean("resultsAnnouncerFacade")
    public ResultsAnnouncerFacade crateForProduction(ResultsCheckerFacade resultsCheckerFacade) {
        return new ResultsAnnouncerFacade(resultsCheckerFacade);
    }

}
