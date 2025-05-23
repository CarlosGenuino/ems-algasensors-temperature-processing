package com.algaworks.algasensors.temperature.processing;

import com.algaworks.algasensors.temperature.processing.common.IdGenerator;
import com.algaworks.algasensors.temperature.processing.common.UUIDV7Utils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

class UUIDv7Test {

    @Test
    void shouldGenerateUUIDV7(){
        UUID uuid1 = IdGenerator.generateTimeBasedUUID();
        OffsetDateTime uuidDateTime = UUIDV7Utils.extractOffsetDateTime(uuid1).truncatedTo(ChronoUnit.MINUTES);
        OffsetDateTime currentOffsetDateTime = OffsetDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        Assertions.assertThat(uuidDateTime).isEqualTo(currentOffsetDateTime);
    }
}
