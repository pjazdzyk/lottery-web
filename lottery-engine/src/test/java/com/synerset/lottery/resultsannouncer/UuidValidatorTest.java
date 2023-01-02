package com.synerset.lottery.resultsannouncer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UuidValidatorTest {

    @Test
    @DisplayName("should pass when provided id is possible to parse to uuid")
    public void isValidUuid_shouldPass_whenParsableUuidIsGiven(){
        // given
        String uuidAsString = "796294d8-f538-4ab0-a671-b0b2a5abd842";

        // when
        boolean actualValidationResult = UuidValidator.isValidUUID(uuidAsString);
        UUID uuid = UUID.fromString(uuidAsString);

        // then
        assertThat(actualValidationResult).isTrue();
        assertThat(uuid.toString()).isEqualTo(uuidAsString);
    }

}
