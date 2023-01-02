package com.synerset.lottery.resultsannouncer;

import com.synerset.lottery.resultschecker.ResultsCheckerFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ResultsAnnouncerConfiguration {

    public ResultsAnnouncerFacade createForTests(ResultsCheckerFacade resultsCheckerFacade) {
        return crateForProduction(resultsCheckerFacade);
    }

    @Bean("resultsAnnouncerFacade")
    public ResultsAnnouncerFacade crateForProduction(ResultsCheckerFacade resultsCheckerFacade) {
        return new ResultsAnnouncerFacadeImpl(resultsCheckerFacade);
    }

}
