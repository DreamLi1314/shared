package com.geoviswtx.controller;

import com.geoviswtx.service.AppProcessService;
import com.geoviswtx.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "游戏操作 Apis")
@RestController
public class GameController {

    private final AppProcessService processService;

    public GameController(AppProcessService processService) {
        this.processService = processService;
    }

    @ApiOperation("建立连接")
    @PostMapping("/game/connect")
    public AppConnectResponse start(@RequestBody AppGameVo tmeGameVo) throws Exception {
        return processService.connect(tmeGameVo.toDto());
    }

    @ApiOperation("断开连接")
    @PostMapping("/game/disConnect")
    public AppResponse disConnect(@RequestBody AppGameVo tmeGameVo) throws Exception {
        return processService.disConnect(tmeGameVo.toDto());
    }

    @ApiOperation("查询连接状态")
    @PostMapping("/game/status/ping")
    public AppPingResponse ping(@RequestBody AppGameVo tmeGameVo) throws Exception {
        return processService.ping(tmeGameVo.toDto());
    }

    @ApiOperation("礼物置顶")
    @PostMapping("/game/gift/top")
    public AppResponse giftTop(@RequestBody GiftTopAppGameVo tmeGameVo) throws Exception {
        return processService.giftTop(tmeGameVo.toDto());
    }

}
