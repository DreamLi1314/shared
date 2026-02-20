package com.geoviswtx.controller;

import com.geoviswtx.dto.AppCallbackDto;
import com.geoviswtx.producer.MQProducerService;
import com.geoviswtx.service.DataProcessService;
import com.geoviswtx.vo.AppResponse;
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
    private final DataProcessService dataProcessService;

    public CallbackController(MQProducerService producerService, DataProcessService dataProcessService) {
        this.producerService = producerService;
        this.dataProcessService = dataProcessService;
    }

    @PostMapping("/ksh/callback/like")
    public AppResponse tmeCallbackLike(@RequestBody AppCallbackDto dto) {
        log.info("tmeCallbackLike: " + dto);

        producerService.sendWithTag(dto, "like");

        dataProcessService.saveLike(dto);

        AppResponse response = new AppResponse();
        response.setResult(1);

        return response;
    }

    @PostMapping("/ksh/callback/comment")
    public AppResponse tmeCallbackComment(@RequestBody AppCallbackDto dto) {
        log.info("tmeCallbackComment: " + dto);

        producerService.sendWithTag(dto, "comment");

        dataProcessService.saveComment(dto);

        AppResponse response = new AppResponse();
        response.setResult(1);

        return response;
    }

    @PostMapping("/ksh/callback/gift")
    public AppResponse tmeCallbackGift(@RequestBody AppCallbackDto dto) {
        log.info("tmeCallbackGift: " + dto);

        producerService.sendWithTag(dto, "gift");

        dataProcessService.saveGift(dto);

        AppResponse response = new AppResponse();
        response.setResult(1);

        return response;
    }

    @PostMapping("/ksh/callback/follow")
    public AppResponse tmeCallbackFollow(@RequestBody AppCallbackDto dto) {
        log.info("tmeCallbackFollow: " + dto);

        producerService.sendWithTag(dto, "follow");

        dataProcessService.saveFollow(dto);

        AppResponse response = new AppResponse();
        response.setResult(1);

        return response;
    }

}
