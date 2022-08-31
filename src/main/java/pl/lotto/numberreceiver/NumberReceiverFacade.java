package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import pl.lotto.drawdategenerator.DrawDateGeneratorFacade;
import pl.lotto.numberreceiver.dto.NumberReceiverResultDto;

public class NumberReceiverFacade {

    private final DrawDateGeneratorFacade drawDateGeneratorFacade = new DrawDateGeneratorFacade();
    private final NumberReceiverRepository receiverRepository;

    public NumberReceiverFacade(NumberReceiverRepository receiverRepository) {
        this.receiverRepository = receiverRepository;
    }

    public NumberReceiverResultDto inputNumbers(List<Integer> numbersFromUser) {
        LocalDateTime creationTIme = drawDateGeneratorFacade.getCurrentDateAndTime();
        LocalDateTime drawTime = drawDateGeneratorFacade.getDrawDate();
        UserCoupon userCoupon = new UserCoupon(creationTIme, drawTime, numbersFromUser);
        //TODO add userToken to database
        UserCoupon save = receiverRepository.save(userCoupon);
        return mapUserTokenToDTO(save);
    }

    private NumberReceiverResultDto mapUserTokenToDTO(UserCoupon userToken) {
        return new NumberReceiverResultDto(userToken.getUuid(), userToken.getTokenCreationDate(), userToken.getResultsDrawDate(),
                userToken.getResultsDrawDate(), userToken.getTypedNumbers());
    }

}


