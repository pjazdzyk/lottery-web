package pl.lotto.numberreceiver;

import pl.lotto.drawdategenerator.DrawDateGeneratorFacade;
import pl.lotto.numberreceiver.dto.NumberReceiverResultDto;

import java.time.LocalDateTime;
import java.util.List;

public class NumberReceiverFacade {

    DrawDateGeneratorFacade drawDateGeneratorFacade = new DrawDateGeneratorFacade();

    public NumberReceiverResultDto inputNumbers(List<Integer> numbersFromUser) {
        LocalDateTime creationTIme = drawDateGeneratorFacade.getCurrentDateAndTime();
        LocalDateTime drawTime = drawDateGeneratorFacade.getDrawDate();
        UserToken userToken = new UserToken(creationTIme, drawTime, numbersFromUser);
        //TODO add userToken to database
        return mapUserTokenToDTO(userToken);
    }

    private NumberReceiverResultDto mapUserTokenToDTO(UserToken userToken) {
        return new NumberReceiverResultDto(userToken.getUuid(), userToken.getTokenCreationDate(), userToken.getResultsDrawDate(),
                userToken.getResultsDrawDate(), userToken.getTypedNumbers());
    }

}


