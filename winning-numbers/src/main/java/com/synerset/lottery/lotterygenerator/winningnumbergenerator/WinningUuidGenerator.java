package com.synerset.lottery.lotterygenerator.winningnumbergenerator;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class WinningUuidGenerator {

    UUID generateRandomUUID() {
        return UUID.randomUUID();
    }

}
