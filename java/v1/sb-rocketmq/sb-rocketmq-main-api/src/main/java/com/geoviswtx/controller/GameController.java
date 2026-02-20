package com.geoviswtx.controller;

import com.geoviswtx.service.TmeProcessService;
import com.geoviswtx.vo.TmeConnectItem;
import com.geoviswtx.vo.TmeGameVo;
import com.geoviswtx.vo.TmePingResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "游戏操作 Apis")
@RestController
public class GameController {

    private final TmeProcessService processService;

    public GameController(TmeProcessService processService) {
        this.processService = processService;
    }

    @ApiOperation("建立连接")
    @PostMapping("/game/connect")
    public TmeConnectItem start(@RequestBody TmeGameVo tmeGameVo) throws Exception {
        return processService.connectAndGetToken(tmeGameVo);
    }

    @ApiOperation("断开连接")
    @PostMapping("/game/disConnect")
    public TmePingResponse disConnect(@RequestBody TmeGameVo tmeGameVo) throws Exception {
        return processService.disConnect(tmeGameVo);
    }

    @ApiOperation("查询连接状态")
    @PostMapping("/game/status/ping")
    public TmePingResponse ping(@RequestBody TmeGameVo tmeGameVo) throws Exception {
        return processService.ping(tmeGameVo);
    }

}
