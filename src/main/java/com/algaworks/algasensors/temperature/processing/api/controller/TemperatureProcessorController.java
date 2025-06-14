package com.algaworks.algasensors.temperature.processing.api.controller;

import com.algaworks.algasensors.temperature.processing.api.model.TemperatureLogOutput;
import com.algaworks.algasensors.temperature.processing.common.IdGenerator;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

import static com.algaworks.algasensors.temperature.processing.infra.rabbitmq.RabbitMQConfig.EXCHANGE_FANOUT_NAME;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/sensors/{sensorId}/temperatures/data")
public class TemperatureProcessorController {

    private final RabbitTemplate rabbitTemplate;

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public void data(@PathVariable TSID sensorId, @RequestBody String input){
        if (input == null || input.isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        double temperature;
        try {
            temperature = Double.parseDouble(input);
        } catch (NumberFormatException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        TemperatureLogOutput logOutput = TemperatureLogOutput.builder()
                .id(IdGenerator.generateTimeBasedUUID())
                .sensorId(sensorId)
                .value(temperature)
                .registeredAt(OffsetDateTime.now())
                .build();

        log.info(logOutput.toString());

        String exchangeName = EXCHANGE_FANOUT_NAME;
        String routingKey = "";
        Object payload = logOutput;

        rabbitTemplate.convertAndSend(exchangeName, routingKey, payload);
    }
}
