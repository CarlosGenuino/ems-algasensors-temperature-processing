package com.algaworks.algasensors.temperature.processing.common;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;

import java.util.UUID;

public class IdGenerator {
    private static final TimeBasedEpochRandomGenerator randomGenerator = Generators.timeBasedEpochRandomGenerator();

    private IdGenerator(){
    }

    public static UUID generateTimeBasedUUID(){
        return randomGenerator.generate();
    }
}
