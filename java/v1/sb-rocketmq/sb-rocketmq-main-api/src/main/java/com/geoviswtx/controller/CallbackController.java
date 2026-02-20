package com.geoviswtx.controller;

import com.geoviswtx.dto.TmeCallbackDto;
import com.geoviswtx.producer.MQProducerService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "回调 Api")
@RestController
@Slf4j
public class CallbackController {

    private final MQProducerService producerService;

    public CallbackController(MQProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/tme/callback")
    public void tmeCallback(@RequestBody TmeCallbackDto dto) {
        log.info("tmeCallback: " + dto);

        producerService.send(dto);
    }

}
